package ci.interpreters.posh;

import java.util.List;

interface PoshCallable {
    int arity();

    Object call(Interpreter interpreter, List<Object> arguments);
}
