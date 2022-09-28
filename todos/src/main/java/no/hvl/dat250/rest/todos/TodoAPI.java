package no.hvl.dat250.rest.todos;

import com.google.gson.Gson;

import java.util.HashMap;

import static spark.Spark.*;

/**
 * Rest-Endpoint.
 */
public class TodoAPI {

    private static HashMap<Long,Todo> todo = new HashMap<>();

    public static void main(String[] args) {
        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        after((req, res) -> res.type("application/json"));

        // Create
        post("/todo", (req, res) -> {
            Gson gson = new Gson();
            Todo t = gson.fromJson(req.body(),Todo.class);
            todo.put(t.getId(), t);
            return t.toJson();
        });

        // Read
        /*get("/todo", (req, res) -> {
            for(Todo t: todo){
                t.toJson();
            }
        });
        */
    }
}
