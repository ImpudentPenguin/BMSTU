using System;
using Xunit;

namespace XmlParser.Tests
{
    public class XmlParserTests
    {
        [Theory(DisplayName = "Parses XML version")]
        [InlineData("1.0")]
        [InlineData("1.1")]
        [InlineData("19.15")]
        public void ParsesVersion(string version)
        {
            //  arrange
            string xml = @$"<?xml version=""{version}""?>";
            var parser = new XmlParser();

            //  act
            var actual = parser.Parse(xml);

            //  assert
            Assert.Equal(version, actual.Version);
        }

        [Theory(DisplayName = "Parses document encoding")]
        [InlineData("UTF-8")]
        [InlineData("UTF-16")]
        [InlineData("ISO-10646-UCS-2")]
        [InlineData("Shift_JIS")]
        [InlineData("some unknown encoding")]
        public void ParsesEncoding(string encoding)
        {
            //  arrange
            string xml = @$"<?xml encoding=""{encoding}""?>";
            var parser = new XmlParser();

            //  act
            var actual = parser.Parse(xml);

            //  assert
            Assert.Equal(encoding, actual.Encoding);
        }

        [Theory(DisplayName = "Parses root element name")]
        [InlineData("elementname")]
        [InlineData("element-name")]
        [InlineData("element_name")]
        [InlineData("俄语")]
        [InlineData("some unknown encoding")]
        public void ParsesRootElementName(string name)
        {
            //  arrange
            string xml = @$"<?xml version=""1.0"" encoding=""UTF-8""?>
<{name}></{name}>";
            var parser = new XmlParser();

            //  act
            var actual = parser.Parse(xml);

            //  assert
            Assert.Equal(name, actual.RootElement.Name);
        }

        [Fact(DisplayName = "Parses root element on same line")]
        public void ParsesRootElementOnSameLine()
        {
            //  arrange
            string xml = @$"<?xml version=""1.0"" encoding=""UTF-8""?><element></element>";
            var parser = new XmlParser();

            //  act
            var actual = parser.Parse(xml);

            //  assert
            Assert.Equal("element", actual.RootElement.Name);
        }

        [Fact(DisplayName = "Parses root element on same line")]
        public void ParsesRootElementOnNewLine()
        {
            //  arrange
            string xml = @$"<?xml version=""1.0"" encoding=""UTF-8""?>
<element></element>";
            var parser = new XmlParser();

            //  act
            var actual = parser.Parse(xml);

            //  assert
            Assert.Equal("element", actual.RootElement.Name);
        }

        [Fact(DisplayName = "Parses root element with spaces in content")]
        public void ParsesRootElementWithSapces()
        {
            //  arrange
            string xml = @$"<?xml version=""1.0"" encoding=""UTF-8""?><element>   </element>";
            var parser = new XmlParser();

            //  act
            var actual = parser.Parse(xml);

            //  assert
            Assert.Equal("element", actual.RootElement.Name);
        }

        [Fact(DisplayName = "Parses root element with child")]
        public void ParsesRootElementWithChild()
        {
            //  arrange
            string xml = @$"<?xml version=""1.0"" encoding=""UTF-8""?>
<employees><employee></employee></employees>";
            var parser = new XmlParser();

            //  act
            var actual = parser.Parse(xml);

            //  assert
            Assert.Equal("employees", actual.RootElement.Name);
        }

        [Fact(DisplayName = "Parses root element with child, spaces & newlines")]
        public void ParsesRootElementWithChildSpacesAndNewlines()
        {
            //  arrange
            string xml = @$"<?xml version=""1.0"" encoding=""UTF-8""?>
<employees>
    <employee></employee>
</employees>";
            var parser = new XmlParser();

            //  act
            var actual = parser.Parse(xml);

            //  assert
            Assert.Equal("employees", actual.RootElement.Name);
        }

        [Fact(DisplayName = "Parses root element with children")]
        public void ParsesRootElementWithChildren()
        {
            //  arrange
            string xml = @$"<?xml version=""1.0"" encoding=""UTF-8""?>
<employees>
    <employee></employee>
    <employee></employee>
    <employee></employee>
</employees>";
            var parser = new XmlParser();

            //  act
            var actual = parser.Parse(xml);

            //  assert
            Assert.Equal("employees", actual.RootElement.Name);
        }

        [Fact(DisplayName = "Parses root element with multi level children")]
        public void ParsesRootElementWithMultiLevelChildren()
        {
            //  arrange
            string xml = @$"<?xml version=""1.0"" encoding=""UTF-8""?>
<employees>
    <employee>
        <home-address>
            <city>Москва</city>
            <street>Ленинский пр-т</street>
            <house>32</house>
            <building>2</building>
            <appartment>212</appartment>
        </home-address>
    </employee>
    <employee></employee>
    <employee></employee>
</employees>";
            var parser = new XmlParser();

            //  act
            var actual = parser.Parse(xml);

            //  assert
            Assert.Equal("employees", actual.RootElement.Name);
        }

        [Fact(DisplayName = "Parses root element attributes")]
        public void ParsesRootElementAttributes()
        {
            //  arrange
            string xml = @$"<?xml version=""1.0"" encoding=""UTF-8""?>
<Employees LastEditTime=""2021-04-19T05:18:23.581Z"" PreviousEditTime=""2021-04-11T09:24:19.836Z"" EditorId=""137"">
    <employee>
        <home-address>
            <city>Москва</city>
            <street>Ленинский пр-т</street>
            <house>32</house>
            <building>2</building>
            <appartment>212</appartment>
        </home-address>
    </employee>
    <employee></employee>
    <employee></employee>
</Employees>";
            var parser = new XmlParser();

            //  act
            var actual = parser.Parse(xml);

            //  assert
            Assert.Equal(3, actual.RootElement.Attributes.Count);
            Assert.Equal("2021-04-19T05:18:23.581Z", actual.RootElement.Attributes["LastEditTime"]);
            Assert.Equal("2021-04-11T09:24:19.836Z", actual.RootElement.Attributes["PreviousEditTime"]);
            Assert.Equal("137", actual.RootElement.Attributes["EditorId"]);
        }

        [Fact(DisplayName = "Parses root element text content")]
        public void ParsesRootElementTextContent()
        {
            //  arrange
            string xml = @$"<?xml version=""1.0"" encoding=""UTF-8""?>
<Employees>Peter, John, Jack</Employees>";
            var parser = new XmlParser();

            //  act
            var actual = parser.Parse(xml);

            //  assert
            Assert.Equal("Peter, John, Jack", actual.RootElement.TextContent);
            Assert.Empty(actual.RootElement.Children);
        }

        [Fact(DisplayName = "Parses multi level children")]
        public void ParsesMultiLevelChildren()
        {
            //  arrange
            string xml = @$"<?xml version=""1.0"" encoding=""UTF-8""?>
<employees>
    <employee EmploymentDate=""2021-04-11T09:24:19.836Z"">
        <home-address>
            <city>Москва</city>
            <street>Ленинский пр-т</street>
            <house>32</house>
            <building>2</building>
            <appartment>212</appartment>
        </home-address>
    </employee>
    <employee></employee>
    <employee></employee>
</employees>";
            var parser = new XmlParser();

            //  act
            var actual = parser.Parse(xml);

            //  assert
            foreach (var child in actual.RootElement.Children)
            {
                Assert.Equal("employee", child.Name);
            }

            Assert.Single(actual.RootElement.Children[0].Children);
            Assert.Equal("2021-04-11T09:24:19.836Z", actual.RootElement.Children[0].Attributes["EmploymentDate"]);
            Assert.Equal("home-address", actual.RootElement.Children[0].Children[0].Name);

            Assert.Equal(5, actual.RootElement.Children[0].Children[0].Children.Count);
            Assert.Equal("city", actual.RootElement.Children[0].Children[0].Children[0].Name);
            Assert.Equal("Москва", actual.RootElement.Children[0].Children[0].Children[0].TextContent);
            Assert.Equal("street", actual.RootElement.Children[0].Children[0].Children[1].Name);
            Assert.Equal("Ленинский пр-т", actual.RootElement.Children[0].Children[0].Children[1].TextContent);
            Assert.Equal("house", actual.RootElement.Children[0].Children[0].Children[2].Name);
            Assert.Equal("32", actual.RootElement.Children[0].Children[0].Children[2].TextContent);
            Assert.Equal("building", actual.RootElement.Children[0].Children[0].Children[3].Name);
            Assert.Equal("2", actual.RootElement.Children[0].Children[0].Children[3].TextContent);
            Assert.Equal("appartment", actual.RootElement.Children[0].Children[0].Children[4].Name);
            Assert.Equal("212", actual.RootElement.Children[0].Children[0].Children[4].TextContent);
        }
    }
}
