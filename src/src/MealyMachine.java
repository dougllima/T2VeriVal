package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by douglas.leite on 25/05/2017.
 */
class MealyMachine {
    int initialStatus;
    HashMap<Integer, Status> statuses;
    HashMap<Integer, HashMap<String, Transition>> transitions;

    MealyMachine() {
        statuses = new HashMap<>();
        transitions = new HashMap<>();
    }
}

class Status {
    String name;
    int id;

    Status(String name, int id) {
        this.name = name;
        this.id = id;
    }

    Status() {
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
