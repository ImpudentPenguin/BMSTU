using System;

namespace Homework04
{
    public readonly struct Inch : IEquatable<Inch>
    {
        internal double Value { get; }

        public Inch(double value)
        {
            Value = value;
        }

        public static Inch operator -(Inch inch)
        {
            return new Inch(-inch.Value);
        }

        public static Inch operator +(Inch inch)
        {
            return new Inch(+inch.Value);
        }

        public static Inch operator +(Inch first, Inch second)
        {
            return new Inch(first.Value + second.Value);
        }

        public static Inch operator -(Inch first, Inch second)
        {
            return new Inch(first.Value - second.Value);
        }

        public static Inch operator *(Inch first, Inch second)
        {
            return new Inch(first.Value * second.Value);
        }

        public static Inch operator /(Inch first, Inch second)
        {
            return new Inch(first.Value / second.Value);
        }

        public static Inch operator +(Inch first, Meter second)
        {
            return new Inch(Math.Round(first.Value + second.Value * 39.37, 2));
        }

        public static Inch operator -(Inch first, Meter second)
        {
            return new Inch(Math.Round(first.Value - second.Value * 39.37, 2));
        }

        public static Inch operator *(Inch first, Meter second)
        {
            return new Inch(Math.Round(first.Value * (second.Value * 39.37), 2));
        }

        public static Inch operator /(Inch first, Meter second)
        {
            return new Inch(Math.Round(first.Value / (second.Value * 39.37), 2));
        }

        public static Inch operator +(Inch first, double second)
        {
            return new Inch(first.Value + second);
        }

        public static Inch operator -(Inch first, double second)
        {
            return new Inch(first.Value - second);
        }

        public static Inch operator *(Inch first, double second)
        {
            return new Inch(first.Value * second);
        }

        public static Inch operator /(Inch first, double second)
        {
            return new Inch(first.Value / second);
        }

        public static bool operator ==(Inch first, Inch second)
        {
            return Math.Abs(first.Value - second.Value) == 0.0;
        }

        public static bool operator !=(Inch first, Inch second)
        {
            return Math.Abs(first.Value - second.Value) != 0.0;
        }

        public static bool operator >=(Inch first, Inch second)
        {
            return first.Value >= second.Value;
        }

        public static bool operator <=(Inch first, Inch second)
        {
            return first.Value <= second.Value;
        }

        public static bool operator >(Inch first, Inch second)
        {
            return first.Value > second.Value;
        }

        public static bool operator <(Inch first, Inch second)
        {
            return first.Value < second.Value;
        }

        public static implicit operator double(Inch inch) => inch.Value;
        
        public static implicit operator Inch(double d) => new Inch(d);
        
        public static explicit operator Inch(Meter meter) => new Inch(Math.Round(meter.Value * 39.37, 2));

        public override string ToString()
        {
            return $"{Value} inch";
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(Value);
        }

        public bool Equals(Inch other)
        {
            return Math.Abs(Value - other.Value) == 0.0;
        }

        public override bool Equals(object obj)
        {
            if (obj is Inch other)
                return Equals(other);

            return false;
        }
    }
}