using System;

namespace XmlParser
{
    public class XmlDocument
    {
        public string Version { get; set; }

        public string Encoding { get; set; }

        public XmlElement RootElement { get; set; }

        public override string ToString()
        {
            var version = Version != null ? $" version=\"{Version}\"" : "";
            var encoding = Encoding != null ? $" encoding=\"{Encoding}\"" : "";
            var elements = RootElement != null ? $"{Environment.NewLine}{RootElement}" : "";

            return $"<?xml{version}{encoding}?>{elements}";
        }
    }
}
