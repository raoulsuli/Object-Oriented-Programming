package utils;

public class Data {
        private String string;
        private int x;
        private int y;
        public Data(final String s, final int x1, final int y1) {
            string = s;
            x = x1;
            y = y1;
        }

        public final int getX() {
            return x;
        }

        public final int getY() {
            return y;
        }

        public final String getString() {
            return string;
        }
}
