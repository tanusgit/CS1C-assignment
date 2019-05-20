package lazyTrees;

public class PrintObject<T> implements Traverser<T> {
        @Override
        public void visit(T x)
        {
            System.out.print(x + " ");
        }
    }
