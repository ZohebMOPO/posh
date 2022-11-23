package ci.interpreters.posh;

import java.util.List;
import java.util.Map;

public class PoshClass implements PoshCallable {
    private final Map<String, PoshFunction> methods;
    final String name;
    final PoshClass superclass;

    PoshClass(String name, PoshClass superclass, Map<String, PoshFunction> methods) {
        this.superclass = superclass;
        this.name = name;
        this.methods = methods;
    }

    PoshFunction findMethod(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }

        if (superclass != null) {
            return superclass.findMethod(name);
        }

        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Object call(Interpreter interpreter,
            List<Object> arguments) {
        PoshInstance instance = new PoshInstance(this);
        PoshFunction initializer = findMethod("init");
        if (initializer != null) {
            initializer.bind(instance).call(interpreter, arguments);
        }

        return instance;
    }

    @Override
    public int arity() {
        PoshFunction initializer = findMethod("init");
        if (initializer == null)
            return 0;
        return initializer.arity();

    }
}
