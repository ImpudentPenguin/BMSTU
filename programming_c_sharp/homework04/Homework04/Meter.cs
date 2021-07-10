using System;

namespace Homework04
{
    public readonly struct Meter : IEquatable<Meter>
    {
        internal double Value { get; }

        public Meter(double value)
        {
            Value = value;
        }

        public static Meter operator -(Meter meter)
        {
            return new Meter(-meter.Value);
        }

        public static Meter operator +(Meter meter)
        {
            return new Meter(+meter.Value);
        }

        public static Meter operator +(Meter first, Meter second)
        {
            return new Meter(first.Value + second.Value);
        }

        public static Meter operator -(Meter first, Meter second)
        {
            return new Meter(first.Value - second.Value);
        }

        public static Meter operator *(Meter first, Meter second)
        {
            return new Meter(first.Value * second.Value);
        }

        public static Meter operator /(Meter first, Meter second)
        {
            return new Meter(first.Value / second.Value);
        }

        public static Meter operator +(Meter first, Inch second)
        {
            return new Meter(Math.Round(first.Value + second.Value / 39.37, 2));
        }

        public static Meter operator -(Meter first, Inch second)
        {
            return new Meter(Math.Round(first.Value - second.Value / 39.37, 2));
        }

        public static Meter operator *(Meter first, Inch second)
        {
            return new Meter(Math.Round(first.Value * (second.Value / 39.37), 2));
        }

        public static Meter operator /(Meter first, Inch second)
        {
            return new Meter(Math.Round(first.Value / (second.Value / 39.37), 2));
        }

        public static Meter operator +(Meter first, double second)
        {
            return new Meter(first.Value + second);
        }

        public static Meter operator -(Meter first, double second)
        {
            return new Meter(first.Value - second);
        }

        public static Meter operator *(Meter first, double second)
        {
            return new Meter(first.Value * second);
        }

        public static Meter operator /(Meter first, double second)
        {
            return new Meter(first.Value / second);
        }

        public static bool operator ==(Meter first, Meter second)
        {
            return Math.Abs(first.Value - second.Value) == 0.0;
        }

        public static bool operator !=(Meter first, Meter second)
        {
            return Math.Abs(first.Value - second.Value) != 0.0;
        }

        public static bool operator >=(Meter first, Meter second)
        {
            return first.Value >= second.Value;
        }

        public static bool operator <=(Meter first, Meter second)
        {
            return first.Value <= second.Value;
        }

        public static bool operator >(Meter first, Meter second)
        {
            return first.Value > second.Value;
        }

        public static bool operator <(Meter first, Meter second)
        {
            return first.Value < second.Value;
        }

        public static implicit operator double(Meter meter) => meter.Value;

        public static implicit operator Meter(double d) => new Meter(d);

        public static explicit operator Meter(Inch inch) => new Meter(Math.Round(inch.Value / 39.37, 2));

        public override string ToString()
        {
            return $"{Value} meter";
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(Value);
        }

        public bool Equals(Meter other)
        {
            return Math.Abs(Value - other.Value) == 0.0;
        }

        public override bool Equals(object obj)
        {
            if (obj is Meter other)
                return Equals(other);

            return false;
        }
    }
}
