using System;
using System.Collections.Generic;
using System.Linq;

namespace XmlParser
{
    public class XmlParser
    {
        public XmlDocument Parse(string xml)
        {
            var data = xml.Split(Environment.NewLine).ToList();

            if (data.Count == 1 && data.First().Contains("</"))
            {
                var head = data
                    .First()
                    .Substring(0, data.First().IndexOf("?>", StringComparison.Ordinal) + 2);
                data.Add(data.First().Replace(head, ""));
                data[0] = head;
            }

            var headAttributes = GetListAttributes(data[0], new[] {"?", "xml"}, "\" ");
            var doc = new XmlDocument();

            foreach (var value in headAttributes)
            {
                if (value.Contains("version"))
                {
                    var version = value.Replace("version=\"", "").Replace("\"", "");
                    doc.Version = version;
                }
                else if (value.Contains("encoding"))
                {
                    var encoding = value.Replace("encoding=\"", "").Replace("\"", "");
                    doc.Encoding = encoding;
                }
            }

            if (data.Count <= 1)
                return doc;

            data.RemoveAt(0);
            doc.RootElement = GetRootElement(null, data);

            return doc;
        }

        private XmlElement GetRootElement(string element, List<string> data, bool isChild = false)
        {
            var xmlElement = new XmlElement();
            string rootElementName = null;
            element ??= data.First().Trim();

            if (element.IndexOf("<", StringComparison.Ordinal) == 0 &&
                element.Trim().IndexOf("/", StringComparison.Ordinal) != 1)
            {
                var resultName = element.Substring(1, element.IndexOf(">", StringComparison.Ordinal) - 1);
                rootElementName = resultName.Contains("=") ? resultName.Split(" ").First() : resultName;
            }

            xmlElement.Name = rootElementName;
            xmlElement.Children = new List<XmlElement>();

            var elementAttributes = GetListAttributes(element, new[] {rootElementName, "/"}, " ");

            if (elementAttributes.Count != 0)
            {
                var dictionary = new Dictionary<string, string>();
                foreach (var elementAttribute in elementAttributes)
                {
                    var res = elementAttribute.Split("=");
                    dictionary[res.First()] = res.Last().Replace("\"", "");
                }

                xmlElement.Attributes = dictionary;
            }

            if (element.Contains("</") && rootElementName != null)
            {
                var res = element
                    .Replace("</", "")
                    .Replace("<", "")
                    .Replace(">", "")
                    .Replace(rootElementName, "");

                xmlElement.TextContent = res == "" ? null : res;

                return xmlElement;
            }

            var startIndex = isChild ? 0 : (data.FindIndex(value => value.Contains($"<{rootElementName}>")) + 1);
            var endIndex = isChild ? data.Count : data.FindIndex(value => value.Contains($"</{rootElementName}>"));
            var childPadding = -1;

            if (isChild)
                childPadding = data.First().IndexOf("<", StringComparison.Ordinal);
            else if (startIndex + 1 < data.Count && startIndex != endIndex)
                childPadding = data[startIndex].IndexOf("<", StringComparison.Ordinal);

            if (childPadding == -1)
                return xmlElement;

            for (var i = startIndex; i < endIndex; i++)
            {
                var isChildrenPadding = data[i].IndexOf("<", StringComparison.Ordinal) == childPadding;
                var isNotCloseTag = data[i].Trim().IndexOf("/", StringComparison.Ordinal) != 1;

                switch (isChildrenPadding)
                {
                    case true when data[i].Contains("</") && isNotCloseTag:
                        xmlElement.Children.Add(GetRootElement(data[i].Trim(), null, true));
                        break;
                    case true when isNotCloseTag:
                    {
                        var childRootName = data[i].Trim()
                            .Substring(1, data[i].Trim().IndexOf(">", StringComparison.Ordinal) - 1)
                            .Split(" ").First();
                        var childEndIndex = data.FindIndex(value => value.Contains($"</{childRootName}>"));
                        var children = new List<string>();
                        for (var j = i + 1; j < childEndIndex; j++)
                        {
                            children.Add(data[j]);
                        }

                        xmlElement.Children.Add(GetRootElement(data[i].Trim(), children, true));
                        break;
                    }
                }
            }

            return xmlElement;
        }

        private List<string> GetListAttributes(string value, string[] replaceChar, string separator)
        {
            foreach (var ch in replaceChar)
            {
                value = value.Replace(ch, "");
            }

            if (value.IndexOf("<", StringComparison.Ordinal) + 1 == value.IndexOf(">", StringComparison.Ordinal))
                return new List<string>();

            var list = value
                .Replace("<", "")
                .Replace(">", "")
                .Trim()
                .Split(separator)
                .ToList();
            list.Remove("");

            return list;
        }
    }
}