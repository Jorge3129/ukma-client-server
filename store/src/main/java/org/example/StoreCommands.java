package org.example;

public class StoreCommands {
    public static class CreateArticleBody {
        public String articleName;
        public double price;
        public int amount;
        public String groupName;

        public CreateArticleBody() {
        }

        public CreateArticleBody(String articleName, double price, int amount, String groupName) {
            this.articleName = articleName;
            this.price = price;
            this.amount = amount;
            this.groupName = groupName;
        }
    }

    public static  class DecrementArticleBody {
        public String articleName;
        public int decrement;

        public DecrementArticleBody() {
        }


        public DecrementArticleBody(String articleName, int decrement) {
            this.articleName = articleName;
            this.decrement = decrement;
        }
    }

    public static class IncrementArticleBody {
        public String articleName;
        public int increment;

        public IncrementArticleBody() {}

        public IncrementArticleBody(String articleName, int increment) {
            this.articleName = articleName;
            this.increment = increment;
        }
    }

    public static  class CreateGroupBody {
        public String name;

        public CreateGroupBody() {
        }


        public CreateGroupBody(String name) {
            this.name = name;
        }
    }

    public static class SetPriceBody {
        public String articleName;
        public double price;

        public SetPriceBody() {
        }


        public SetPriceBody(String articleName, double price) {
            this.articleName = articleName;
            this.price = price;
        }
    }

}

