package taxi.tools;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class ObjectToJson implements ResponseTransformer {
    private final Gson gson = new Gson();

    @Override
    public String render(Object model) throws Exception {
        return gson.toJson(model);
    }

}
