using Xunit;

namespace Homework04.Tests
{
    public class MeterTest
    {
        [Fact(DisplayName = "Change value with unar minus")]
        public void ChangeValueWithUnarMinus()
        {
            var meter = new Meter(2.0);
            
            //  act
            var actual = -meter;
            
            //  assert
            Assert.Equal(new Meter(-2.0), actual);
        }
        
        [Fact(DisplayName = "Add two meters objects with operator +")]
        public void AddTwoInchObjects()
        {
            var first = new Meter(2.0);
            var second = new Meter(4.0);
            
            //  act
            var actual = first + second;
            
            //  assert
            Assert.Equal(new Meter(6.0), actual);
        }
        
        [Fact(DisplayName = "Subtract two meters objects with operator -")]
        public void SubtractTwoInchObjects()
        {
            var first = new Meter(10.0);
            var second = new Meter(4.0);
            
            //  act
            var actual = first - second;
            
            //  assert
            Assert.Equal(new Meter(6.0), actual);
        }
        
        [Fact(DisplayName = "Multiply two meters objects with operator *")]
        public void MultiplyTwoInchObjects()
        {
            var first = new Meter(10.0);
            var second = new Meter(4.0);
            
            //  act
            var actual = first * second;
            
            //  assert
            Assert.Equal(new Meter(40.0), actual);
        }
        
        [Fact(DisplayName = "Divide two meters objects with operator /")]
        public void DivideTwoInchObjects()
        {
            var first = new Meter(40.0);
            var second = new Meter(4.0);
            
            //  act
            var actual = first / second;
            
            //  assert
            Assert.Equal(new Meter(10.0), actual);
        }
        
        [Fact(DisplayName = "Add meters with inch with operator +")]
        public void AddMetersWithInches()
        {
            var meter = new Meter(2.0);
            var inch = new Inch(4.0);
            
            //  act
            var actual = meter + inch;

            //  assert
            Assert.Equal(new Meter(2.10), actual);
        }
        
        [Fact(DisplayName = "Subtract inches from meters with operator -")]
        public void SubtractInchesFromMeters()
        {
            var meter = new Meter(200.0);
            var inch = new Inch(100.0);
            
            //  act
            var actual = meter - inch;

            //  assert
            Assert.Equal(new Meter(197.46), actual);
        }
 
        [Fact(DisplayName = "Multiply meters with inches with operator *")]
        public void MultiplyInchesWithMeters()
        {
            var meter = new Meter(10.0);
            var inch = new Inch(100.0);
            
            //  act
            var actual = meter * inch;

            //  assert
            Assert.Equal(new Meter(25.40), actual);
        }
        
        [Fact(DisplayName = "Divide meters with inches with operator -")]
        public void DivideMetersWithInches()
        {
            var meter = new Meter(40.0);
            var inch = new Inch(100.0);
            
            //  act
            var actual = meter / inch;

            //  assert
            Assert.Equal(new Meter(15.75), actual);
        }

        [Fact(DisplayName = "Add meter with double with operator +")]
        public void AddInchWithDouble()
        {
            var meter = new Meter(2.0);
            var d = 4.0;
            
            //  act
            var actual = meter + d;
            
            //  assert
            Assert.Equal(new Meter(6.0), actual);
        }
        
        [Fact(DisplayName = "Subtract double from meters with operator -")]
        public void SubtractDoubleFromInches()
        {
            var meter = new Meter(26.0);
            var d = 4.0;
            
            //  act
            var actual = meter - d;
            
            //  assert
            Assert.Equal(new Meter(22.00), actual);
        }
        
        [Fact(DisplayName = "Multiply double with meters with operator -")]
        public void MultiplyDoubleWithInches()
        {
            var meter = new Meter(10.0);
            var d = 4.0;
            
            //  act
            var actual = meter * d;
            
            //  assert
            Assert.Equal(new Meter(40.0), actual);
        }
        
        [Fact(DisplayName = "Divide double with meters with operator -")]
        public void DivideDoubleWithInches()
        {
            var meter = new Meter(40.0);
            var d = 4.0;
            
            //  act
            var actual = meter / d;
            
            //  assert
            Assert.Equal(new Meter(10.0), actual);
        }
        
        [Fact(DisplayName = "Cast inch in meter with explicit operator")]
        public void CastInchInMeter()
        {
            var inch = new Inch(100.0);

            //  act
            var actual = (Meter) inch;
            
            //  assert
            Assert.Equal(new Meter(2.54), actual);
        }
        
        [Fact(DisplayName = "Cast meter in double with implicit operator")]
        public void CastDoubleInMeter()
        {
            var meter = new Meter(4.0);

            //  act
            double actual = meter;
            
            //  assert
            Assert.Equal(4.0, actual);
        }
        
        [Fact(DisplayName = "Cast double in meter with implicit operator")]
        public void CastDoubleInMeterWithImplicitOperator()
        {
            double d = 4.0;
        
            //  act
            Meter actual = d;
            
            //  assert
            Assert.Equal(new Meter(4.0), actual);
        }
    }
}