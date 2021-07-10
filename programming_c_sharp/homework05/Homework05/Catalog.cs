using System.Collections;
using System.Collections.Generic;
using System.Text;

namespace Homework05
{
    public class Catalog<T> : IEnumerable<T>
    {
        private Node<T> _first; // первый элемент
        private Node<T> _next; // следующий элемент списка
        private int _length; // длина списка

        public int Size()
        {
            return _length;
        }

        public void Add(T data)
        {
            var node = new Node<T>(data);

            if (_first == null)
                _first = node;
            else
                _next.Next = node;

            _next = node;
            _length++;
        }

        public void Remove(T data)
        {
            var current = _first;
            Node<T> prev = null;

            while (current != null)
            {
                if (current.Data.Equals(data))
                {
                    if (prev == null)
                    {
                        _first = _first.Next;

                        if (_first == null)
                            _next = null;
                    }
                    else
                    {
                        prev.Next = current.Next;

                        if (current.Next == null)
                            _next = prev;
                    }

                    _length--;
                    break;
                }

                prev = current;
                current = current.Next;
            }
        }

        public bool Contains(T data)
        {
            var current = _first;

            while (current != null)
            {
                if (current.Data.Equals(data))
                    return true;

                current = current.Next;
            }

            return false;
        }

        public void Clear()
        {
            _first = null;
            _next = null;
            _length = 0;
        }

        public bool IsEmpty()
        {
            return _length == 0;
        }

        public override string ToString()
        {
            var builder = new StringBuilder();
            
            foreach (var item in this)
            {
                builder.Append($"{item}, ");
            }
        
            builder.Replace(", ", "", builder.Length - 2, 2);
        
            return builder.ToString();
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return ((IEnumerable) this).GetEnumerator();
        }

        IEnumerator<T> IEnumerable<T>.GetEnumerator()
        {
            var current = _first;

            while (current != null)
            {
                yield return current.Data;
                current = current.Next;
            }
        }
    }
}