using System;
using System.Collections.Generic;
using System.Text;

namespace XmlParser
{
    public class XmlElement
    {
        public string Name { get; set; }

        public Dictionary<string, string> Attributes { get; set; }

        public List<XmlElement> Children { get; set; }

        public string TextContent { get; set; }

        public override string ToString()
        {
            return GetElements(this, 0);
        }

        private static string GetElements(XmlElement element, int padding)
        {
            var attributes = new StringBuilder();

            if (element.Attributes != null)
                foreach (var (key, value) in element.Attributes)
                {
                    attributes.Append($" {key}=\"{value}\"");
                }

            var result = new StringBuilder("".PadLeft(padding) + $"<{element.Name}{attributes}>");

            if (element.Children != null && element.Children?.Count != 0)
            {
                foreach (var xmlElement in element.Children)
                {
                    result.Append(Environment.NewLine + GetElements(xmlElement, padding + 4));
                }

                result.Append(Environment.NewLine + "".PadLeft(padding));
            }
            else if (element.TextContent != null)
                result.Append(element.TextContent);

            result.Append($"</{element.Name}>");

            return result.ToString();
        }
    }
}