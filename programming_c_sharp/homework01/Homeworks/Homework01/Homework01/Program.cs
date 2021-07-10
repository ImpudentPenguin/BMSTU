using System;
using System.Collections.Generic;

namespace Homework01
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            var dictionary = GetDictionary();
            var rand = new Random();
            var info = new InfoIteration();

            while (true)
            {
                Console.WriteLine("Выберите язык для тестирования скорости печати:\n1 - Russian, 2 - English");
                var line = Console.ReadLine();
                Languages languages;

                try
                {
                    languages = int.Parse(line ?? string.Empty) == 1 ? Languages.Russian : Languages.English;
                }
                catch (Exception)
                {
                    Console.WriteLine("Кажется, вы ввели не число");
                    continue;
                }

                var texts = dictionary[languages];

                Console.WriteLine("Тест на скорость печати:\n");
                var startedAt = DateTime.Now;
                info.IndexText = rand.Next(texts.Length);
                var text = texts[info.IndexText];

                Console.WriteLine(text);
                var textResult = Console.ReadLine();
                info.Time = DateTime.Now - startedAt;

                Console.WriteLine("\nВаш результат:");

                if (string.IsNullOrEmpty(textResult))
                    Console.WriteLine("Кажется, вы ничего не ввели.");
                else if (text.Equals(textResult))
                    Console.WriteLine("В вашем тексте нет ошибок.");
                else
                {
                    info.MisPrintCount = StringCompare.CheckTexts(text, textResult);
                    Console.WriteLine(
                        $"В вашем тексте {info.MisPrintCount} {Utils.Plural(info.MisPrintCount, "опечатка", "опечатки", "опечаток")}");
                }

                Console.WriteLine($"Скорость печати составила: {info.GetTimeFormated()}\n");
                info.AddResult(textResult?.Length ?? 0);

                Console.WriteLine(
                    "Хотите пройти тест еще раз? Введите 'exit' для выхода или нажмите enter для продолжения");
                var answer = Console.ReadLine();

                if (answer != null && answer.Equals("exit"))
                {
                    Console.WriteLine(
                        $"У вас было {info.GetCountResult()} {Utils.Plural(info.GetCountResult(), "попытка", "попытки", "попыток")}.");

                    Console.WriteLine($"Лучшая скорость: {info.GetBestTime()}");

                    if (info.GetCountResult() > 1)
                    {
                        Console.WriteLine($"Худшая скорость: {info.GetWorseTime()}");
                        Console.WriteLine($"Средняя скорость: {info.GetAverageTime()}");
                    }

                    break;
                }
            }
        }

        private static Dictionary<Languages, string[]> GetDictionary()
        {
            var dictionary = new Dictionary<Languages, string[]>();

            string[] russianTexts =
            {
                "Иногда люди могут созерцать поразительное явление: ночные небеса словно " +
                "вспыхивают. Игривые радуги окрашивают небо в красные, желтые, фиолетовые и " +
                "зеленые цвета - создается впечатление, будто смотришь в калейдоскоп. " +
                "Это называют северным сиянием. Самые яркие цвета северного сияния можно " +
                "наблюдать вблизи полюсов, в Арктике и Антарктике. А в средние века это чудо " +
                "иногда раскрашивало даже небеса над Европой.",
                "Количество пингвинов, которые собираются на одном месте, очень велико. " +
                "Днем и ночью высаживается на сушу 30000-40000 особей. Те, что на суше, выстраиваются, " +
                "словно солдаты, не только рядами, но и по росту: молодые - в одном месте, те, что линяют, " +
                "- во втором, самки на яйцах - в третьем, а самцы - в четвертом. Все пингвины живут в южном полушарии. " +
                "Значительную часть года они посвящают размножению.",
                "В штате Айдахо обнаружена гигансткая креветка неизвестного вида. Это хищное ракообразное, " +
                "достигающее восьми сантиметров в длину, водится в местах не характерных для креветок - в часто " +
                "пересыхающих прудах. Гигантская креветка любит охотиться на своих меньших родственников. " +
                "Огромные размеры и необычная стойкость к неблагоприятным условиям среды отличают этого рачка от " +
                "креветок других 300 видов.",
                "В начале третьего века до нашей эры на маленьком острове Фарос около Александрии был построен " +
                "самый высокий маяк в мире высотой 120 метров. Его проектировали греческие ученые, а строителем был " +
                "архитектор Сострат. Сострат начал с того, что построил огромный мол - морскую дамбу, которая соединяла " +
                "Александрию с островом. По этой дамбе подвозили строительный материал. И через 5 лет маяк удалось построить."
            };

            string[] englishTexts =
            {
                "The origin of the Olympic Games was in Greece. The first Games took place in a valley called ‘Olympia’ " +
                "and the Games got their name from this place. In those days the Games took place every four years and they " +
                "lasted for five days. During the five days there were athletics competitions and competitions in music and poetry. " +
                "The original Olympic Games were only for men.",
                "I’m having a great time here in Sydney. The different sports are exciting, and there are lots of other exciting " +
                "things too. For example the mascots are really great! They are called Olly, Syd and Millie. They are Australian" +
                " animals and they are the symbols of the Sydney Games. The kookaburra is an Australian bird. She got her name, Olly, " +
                "from the word Olympics. She’s a symbol of friendship and honesty. Then there’s Syd (from Sydney). He’s a platypus with " +
                "a duck’s nose. He’s the symbol of the city of Sydney and its people. The third mascot is Millie. She’s an Australian " +
                "animal — an echidna. She’s the symbol of the new millennium. So now I’ve got a mascot too. He’s called Ozzie (from Aus­tralia) " +
                "and he’s a cute, cuddly koala.",
                "Do you want to know the history of jeans? In 1850 a young man, Levi Strauss, came to California from Germany. " +
                "California was famous for its gold. Many people were working there. They were looking for gold and needed strong clothes. " +
                "First Levi Strauss sold canvas to workers. Canvas was strong and soon Levi used it to make jeans. All workers liked his jeans " +
                "and bought them. His first jeans had no colour. Then Levi coloured his jeans. Today everyone in the world knows the famous blue jeans of Levi Strauss.",
                "The Olympic Games have their own flag and motto. The flag is white with five circles. The circles represent the five continents " +
                "of Africa, Asia, Australia, Europe and North and South America. The circles are black, blue, green, red and yellow. The flag of every " +
                "country in the games has at least one of these colours. The motto of the Olympics is Faster, higher, stronger."
            };

            dictionary.Add(Languages.Russian, russianTexts);
            dictionary.Add(Languages.English, englishTexts);

            return dictionary;
        }
    }
}