using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xunit;

namespace XmlParser.Tests
{
    public class XmlDocumentTests
    {
        [Fact(DisplayName = "ToString() without version, encoding and root")]
        public void ToStringEmpty()
        {
            //  arrange
            var doc = new XmlDocument();
            string expected = @$"<?xml?>";

            //  act
            var actual = doc.ToString();

            //  assert
            Assert.Equal(expected, actual);
        }

        [Fact(DisplayName = "ToString() without encoding and root")]
        public void ToStringWithVersion()
        {
            //  arrange
            var doc = new XmlDocument { Version = "1.0" };
            string expected = @$"<?xml version=""1.0""?>";

            //  act
            var actual = doc.ToString();

            //  assert
            Assert.Equal(expected, actual);
        }

        [Fact(DisplayName = "ToString() without root")]
        public void ToStringWithVersionAndEncoding()
        {
            //  arrange
            var doc = new XmlDocument { Version = "1.0", Encoding = "UTF-8" };
            string expected = @$"<?xml version=""1.0"" encoding=""UTF-8""?>";

            //  act
            var actual = doc.ToString();

            //  assert
            Assert.Equal(expected, actual);
        }

        [Fact(DisplayName = "ToString() method pretty prints xml")]
        public void ToStringTest()
        {
            //  arrange
            string xml = @$"<?xml version=""1.0"" encoding=""UTF-8""?>
<employees>
    <employee>Foo</employee>
    <employee>Bar</employee>
    <employee>Buzz</employee>
</employees>";
            var parser = new XmlParser();
            var doc = new XmlDocument 
            { 
                Version = "1.0", 
                Encoding = "UTF-8",
                RootElement = new XmlElement
                {
                    Name = "employees",
                    Children = new List<XmlElement>
                    {
                        new XmlElement { Name = "employee", TextContent = "Foo" },
                        new XmlElement { Name = "employee", TextContent = "Foo" },
                        new XmlElement { Name = "employee", TextContent = "Foo" },
                    }
                },
            };

            //  act
            var actual = parser.Parse(xml);

            //  assert
            Assert.Equal(xml, actual.ToString());
        }
    }
}
