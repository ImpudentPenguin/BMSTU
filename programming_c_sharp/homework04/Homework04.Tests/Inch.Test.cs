using Xunit;

namespace Homework04.Tests
{
    public class InchTest
    {
        [Fact(DisplayName = "Change value with unar minus")]
        public void ChangeValueWithUnarMinus()
        {
            var inch = new Inch(2.0);

            //  act
            var actual = -inch;

            //  assert
            Assert.Equal(new Inch(-2.0), actual);
        }

        [Fact(DisplayName = "Add two inch objects with operator +")]
        public void AddTwoInchObjects()
        {
            var first = new Inch(2.0);
            var second = new Inch(4.0);

            //  act
            var actual = first + second;

            //  assert
            Assert.Equal(new Inch(6.0), actual);
        }

        [Fact(DisplayName = "Subtract two inch objects with operator -")]
        public void SubtractTwoInchObjects()
        {
            var first = new Inch(10.0);
            var second = new Inch(4.0);

            //  act
            var actual = first - second;

            //  assert
            Assert.Equal(new Inch(6.0), actual);
        }

        [Fact(DisplayName = "Multiply two inch objects with operator *")]
        public void MultiplyTwoInchObjects()
        {
            var first = new Inch(10.0);
            var second = new Inch(4.0);

            //  act
            var actual = first * second;

            //  assert
            Assert.Equal(new Inch(40.0), actual);
        }

        [Fact(DisplayName = "Divide two inch objects with operator /")]
        public void DivideTwoInchObjects()
        {
            var first = new Inch(40.0);
            var second = new Inch(4.0);

            //  act
            var actual = first / second;

            //  assert
            Assert.Equal(new Inch(10.0), actual);
        }

        [Fact(DisplayName = "Add inch with meters with operator +")]
        public void AddInchWithMeters()
        {
            var inch = new Inch(2.0);
            var meter = new Meter(4.0);

            //  act
            var actual = inch + meter;

            //  assert
            Assert.Equal(new Inch(159.48), actual);
        }

        [Fact(DisplayName = "Subtract meters from inches with operator -")]
        public void SubtractMetersFromInches()
        {
            var inch = new Inch(200.0);
            var meter = new Meter(4.0);

            //  act
            var actual = inch - meter;

            //  assert
            Assert.Equal(new Inch(42.52), actual);
        }

        [Fact(DisplayName = "Multiply meters with inches with operator *")]
        public void MultiplyMetersWithInches()
        {
            var inch = new Inch(10.0);
            var meter = new Meter(4.0);

            //  act
            var actual = inch * meter;

            //  assert
            Assert.Equal(new Inch(1574.8), actual);
        }

        [Fact(DisplayName = "Divide meters with inches with operator /")]
        public void DivideMetersWithInches()
        {
            var inch = new Inch(40.0);
            var meter = new Meter(4.0);

            //  act
            var actual = inch / meter;

            //  assert
            Assert.Equal(new Inch(0.25), actual);
        }

        [Fact(DisplayName = "Add inch with double with operator +")]
        public void AddInchWithDouble()
        {
            var inch = new Inch(2.0);
            var d = 4.0;

            //  act
            var actual = inch + d;

            //  assert
            Assert.Equal(new Inch(6.0), actual);
        }

        [Fact(DisplayName = "Subtract double from inches with operator -")]
        public void SubtractDoubleFromInches()
        {
            var inch = new Inch(26.0);
            var d = 4.0;

            //  act
            var actual = inch - d;

            //  assert
            Assert.Equal(new Inch(22.00), actual);
        }

        [Fact(DisplayName = "Multiply double with inches with operator *")]
        public void MultiplyDoubleWithInches()
        {
            var inch = new Inch(10.0);
            var d = 4.0;

            //  act
            var actual = inch * d;

            //  assert
            Assert.Equal(new Inch(40.0), actual);
        }

        [Fact(DisplayName = "Divide double with inches with operator /")]
        public void DivideDoubleWithInches()
        {
            var inch = new Inch(40.0);
            var d = 4.0;

            //  act
            var actual = inch / d;

            //  assert
            Assert.Equal(new Inch(10.0), actual);
        }

        [Fact(DisplayName = "Cast meter in inch with explicit operator")]
        public void CastMeterInInch()
        {
            var meter = new Meter(4.0);

            //  act
            var actual = (Inch) meter;

            //  assert
            Assert.Equal(new Inch(157.48), actual);
        }

        [Fact(DisplayName = "Cast inch in double with implicit operator")]
        public void CastDoubleInInch()
        {
            var inch = new Inch(4.0);

            //  act
            double actual = inch;

            //  assert
            Assert.Equal(4.0, actual);
        }

        [Fact(DisplayName = "Cast double in inch with implicit operator")]
        public void CastDoubleInInchWithImplicitOperator()
        {
            double d = 4.0;

            //  act
            Inch actual = d;

            //  assert
            Assert.Equal(new Inch(4.0), actual);
        }
    }
}