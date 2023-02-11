package manager.serverTaskManager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class HttpTaskManagerJsonSerializer implements JsonSerializer<HttpTaskManager> {
    @Override
    public JsonElement serialize(HttpTaskManager manager, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("tasks", context.serialize(manager.getTasksHashMap()));
        obj.add("subtasks", context.serialize(manager.getSubtasksHashMap()));
        obj.add("epics", context.serialize(manager.getEpicsHashMap()));
        obj.add("history", context.serialize(manager.getHistory()));
        return obj;
    }
}
