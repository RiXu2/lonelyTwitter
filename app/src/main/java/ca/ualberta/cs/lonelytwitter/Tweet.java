package ca.ualberta.cs.lonelytwitter;

import java.util.Date;


public class Tweet {
    protected String message;
    protected Date date;
    protected  mood_2 mood;
    protected String total_mood;

    public Tweet(String message){
        this.message = message;
        this.mood = new mood_2();
        this.total_mood = this.mood.curr_mood + this.mood.check_date();
    }
    public Tweet(String message, Date date){
        this.message = message;
        this.mood = new mood_2();
        this.date = date;
    }
    public void add_mood1(){
        mood_1 mood1 = new mood_1();
        this.total_mood = this.total_mood + mood1.current_mood()+"\n";
    }
    public void add_mood2(){
        mood_2 mood2 = new mood_2();
        this.total_mood = this.total_mood + mood2.current_mood()+"\n";
    }
    public interface check_and_change{
        public void change_date(Date new_date);
        public String check_date();
    }

    public abstract class base_mood{
        public abstract String current_mood();
    }

    public class mood_1 extends base_mood implements check_and_change{
        private Date curr_date;
        private String curr_mood;
        public mood_1(){
            this.curr_date = new Date();
            this.curr_mood = "I feel so board.";
        }
        public String current_mood(){
            String str_date = this.curr_date.toString();
            String full_mood = str_date + this.curr_mood;
            this.curr_mood = full_mood;
            return this.curr_mood;
        }
        public String check_date(){
            String time = this.curr_date.toString();
            return time;
        }
        public void change_date(Date new_date){
            this.curr_date = new_date;
        }


    }
    public class mood_2 extends base_mood implements check_and_change{
        private Date curr_date;
        private String curr_mood;
        private String[] mood_list = {"sad","mad","messed up","ridiculous","happy","excited","mild","peace","smooth"};
        public mood_2(){
            this.curr_date = new Date();
            this.curr_mood = "emm...";
        }

        @Override
        public String current_mood() {
            int num = (int)(Math.random()*9);
            this.curr_date = new Date();
            this.curr_mood = mood_list[num];
            String str_date = this.curr_date.toString();
            String full_mood = str_date + this.curr_mood;
            this.curr_mood = full_mood;
            return this.curr_mood;
        }

        public void change_date(Date new_date){
            this.curr_date = new_date;
        }

        public String check_date() {
            String time = this.curr_date.toString();
            return time;
        }
    }
}
