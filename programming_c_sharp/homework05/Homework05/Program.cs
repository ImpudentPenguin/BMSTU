using System;

namespace Homework05
{
    internal static class Program
    {
        private static void Main(string[] args)
        {
            // Задание 1
            var catalog = new Catalog<double>();
            var rand = new Random();
            var randomIndex = rand.Next(0, 10);
            var valueForRemove = 0.0;

            for (var i = 0; i < 10; i++)
            {
                var value = Math.Round(rand.NextDouble(), 2);
                catalog.Add(value);

                if (i == randomIndex)
                    valueForRemove = value;
            }
            
            Console.WriteLine($"Размер списка: {catalog.Size()}");
            Console.WriteLine(catalog.ToString());

            PrintResultAboutElement(catalog.Contains(valueForRemove), valueForRemove);

            catalog.Remove(valueForRemove);

            Console.WriteLine(catalog.ToString());

            PrintResultAboutElement(catalog.Contains(valueForRemove), valueForRemove);
        }

        private static void PrintResultAboutElement(bool isContains, double value)
        {
            Console.WriteLine(isContains
                ? $"{Environment.NewLine}Значение {value} присутствует"
                : $"{Environment.NewLine}Значение {value} отсутствует");
        }
    }
}