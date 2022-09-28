package no.hvl.dat250.rest.todos;

import com.google.gson.Gson;

import java.util.ArrayList;
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
        post("/todos", (req, res) -> {
            Gson gson = new Gson();
            Todo t = gson.fromJson(req.body(),Todo.class);
            if(t.getId() == null) {
                t = new Todo(new Long(todo.size()+1), t.getSummary(), t.getDescription());
            }
            todo.put(t.getId(), t);
            return gson.toJson(t);
        });

        // Read one
        get("/todos/:id", (req, res) -> {
            Gson gson = new Gson();
            Long id = null;
            try {
                id = new Long(req.params(":id"));
            } catch (NumberFormatException e){
                return "The id \"notANumber\" is not a number!";
            }
            if (!todo.containsKey(id))
                return "Todo with the id \"" + id + "\" not found!";
            return gson.toJson(todo.get(id));
        });

        get("/todos", (req, res) -> {
            Gson gson = new Gson();
            return gson.toJson(new ArrayList<Todo>(todo.values()));
        });

        put("/todos/:id", (req, res) -> {
            Gson gson = new Gson();
            Long id = null;
            try {
                id = new Long(req.params(":id"));
            } catch (NumberFormatException e){
                return "The id \"notANumber\" is not a number!";
            }
            todo.put(id, gson.fromJson(req.body(),Todo.class));
            return gson.toJson(todo.get(id));
        });

        delete("/todos/:id", (req, res) -> {
            Gson gson = new Gson();
            Long id = null;
            try {
                id = new Long(req.params(":id"));
            } catch (NumberFormatException e){
                return "The id \"notANumber\" is not a number!";
            }
            if (!todo.containsKey(id))
                return "Todo with the id \"" + id + "\" not found!";
            todo.remove(id);
            return gson.toJson(todo.get(id));
        });
    }
}
