using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xunit;

namespace XmlParser.Tests
{
    public class XmlElementTests
    {
        [Fact(DisplayName = "ToString() method pretty prints xml")]
        public void ToStringTest()
        {
            //  arrange
            string expected = @$"<employees>
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
            var doc = new XmlElement
            {
                Name = "employees",
                Children = new List<XmlElement>
                {
                    new XmlElement
                    {
                        Name = "employee",
                        Attributes = new Dictionary<string, string> { { "EmploymentDate", "2021-04-11T09:24:19.836Z"} },
                        Children = new List<XmlElement>
                        {
                            new XmlElement
                            {
                                Name = "home-address",
                                Children = new List<XmlElement>
                                {
                                    new XmlElement { Name = "city", TextContent = "Москва" },
                                    new XmlElement { Name = "street", TextContent = "Ленинский пр-т" },
                                    new XmlElement { Name = "house", TextContent = "32" },
                                    new XmlElement { Name = "building", TextContent = "2" },
                                    new XmlElement { Name = "appartment", TextContent = "212" }
                                }
                            }
                        }
                    },
                    new XmlElement { Name = "employee" },
                    new XmlElement { Name = "employee" },
                }
            };

            //  act
            var actual = doc.ToString();

            //  assert
            Assert.Equal(expected, actual.ToString());
        }
    }
}
