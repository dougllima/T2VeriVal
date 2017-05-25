package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by douglas.leite on 25/05/2017.
 */
class MealyMachine {
    List<Status> statuses;
    HashMap<Status, List<Transition>> transitions;

    MealyMachine() {
        statuses = new ArrayList<>();
        transitions = new HashMap<>();
    }
}

class Status {
    String name;
    int id;
    boolean initial;

    Status(String name, int id, boolean initial) {
        this.name = name;
        this.id = id;
        this.initial = initial;
    }
    Status(){
    }
}

class Transition {
    Status from;
    Status to;

    String read;
    String transout;

    Transition() {
    }
}
