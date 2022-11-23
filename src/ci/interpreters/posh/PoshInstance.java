package ci.interpreters.posh;

import java.util.HashMap;
import java.util.Map;

public class PoshInstance {
    private PoshClass klass;
    private final Map<String, Object> fields = new HashMap<>();

    PoshInstance(PoshClass klass) {
        this.klass = klass;
    }

    Object get(token name) {
        if (fields.containsKey(name.lexeme)) {
            return fields.get(name.lexeme);
        }

        PoshFunction method = klass.findMethod(name.lexeme);
        if (method != null)
            return method;

        throw new RuntimeError(name,
                "Undefined property '" + name.lexeme + "'.");
    }

    void set(token name, Object value) {
        fields.put(name.lexeme, value);
    }

    @Override
    public String toString() {
        return klass.name + " instance";
    }
}
