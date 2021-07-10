using System;

namespace NumericAnalysis
{
    public class IntegralCalculus
    {
        public static double Calculate(Func<double, double> func, double x1, double x2, double precision)
        {
            return Trapezium(func, x1, x2, precision);
        }
        
        /// <summary>
        /// Приближенное вычисление определенного интеграла методом трапеций с заданной точностью
        /// </summary>
        /// <param name="func">Функция</param>
        /// <param name="x1">Нижняя граница интегрирования</param>
        /// <param name="x2">Верхняя граница интегрирования</param>
        /// <param name="precision">Заданная точность</param>
        /// <returns>Приближенное значение определенного интеграла</returns>
        private static double Trapezium(Func<double, double> func, double x1, double x2, double precision)
        {
            double n = 2;
            double h = (x2 - x1) / n;
            double integral = 0.5 * (x1 + x2) * h;
            double integralOld = 0;
            double sum = 0;

            while (Math.Abs(integral - integralOld) > precision)
            {
                integralOld = integral;
                h = (x2 - x1) / n;
                for (int i = 0; i < n / 2; i++)
                    sum += func(x1 + (2 * i + 1) * h);
                
                integral = h * sum;
                n *= 2;
            }

            return integral;
        }
    }
}