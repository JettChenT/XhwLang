import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class XhwRuntime {
    public static final int MEM_SIZE = 10000;

    public static Object parseValue(String s){
        if (s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        } else if (Character.isDigit(s.charAt(0))) {
            return Integer.parseInt(s);
        }
        return s;
    }

    public static Operation getOp(String s){
        return switch (s) {
            case "\uD83D\uDC80" -> Operation.PointerLeft;
            case "\uD83D\uDE0D" -> Operation.PointerRight;
            case "\uD83D\uDE03" -> Operation.Incr;
            case "☹️" -> Operation.Decr;
            case "\uD83C\uDF7D️" -> Operation.Food;
            case "\uD83D\uDDA8️" -> Operation.Print;
            case "✍️" -> Operation.Write;
            case "🤗" -> Operation.Open;
            case "\uD83E\uDEE3" -> Operation.Close;
            default -> throw new RuntimeException("Unknown operation: " + s);
        };
    }

    public static ArrayList<Instruction> parse(String xhcode){
        String[] parts = xhcode.split("\\s+");
        ArrayList<Instruction> res = new ArrayList<Instruction>();
        for (int i = 0; i < parts.length; i++) {
            Operation op = getOp(parts[i].substring(3));
            Object dat = switch (op) {
                case Write -> parseValue(parts[++i]);
                default -> null;
            };
            res.add(new Instruction(op, dat));
        }
        return res;
    }

    public static int[] jmpTable(ArrayList<Instruction> instructions){
        int[] res = new int[instructions.size()];
        Arrays.fill(res, -1);
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < instructions.size(); i++) {
            Instruction cur = instructions.get(i);
            switch (cur.op()) {
                case Open -> stack.push(i);
                case Close -> {
                    int j = stack.pop();
                    res[i] = j;
                    res[j] = i;
                }
            }
        }
        return res;
    }

    public static void run(String xhcode){
        ArrayList<Instruction> instructions = parse(xhcode);
        int[] jmpTable = jmpTable(instructions);
        Object[] mem = new Object[MEM_SIZE];
        int ptr = 0, memptr = 0;
        while(ptr<instructions.size()){
            Instruction cur = instructions.get(ptr);
            switch (cur.op()){
                case PointerLeft -> memptr--;
                case PointerRight -> memptr++;
                case Incr -> mem[memptr] = (int)mem[memptr] + 1;
                case Decr -> mem[memptr] = (int)mem[memptr] - 1;
                case Food -> mem[memptr] = "\uD83E\uDD2E";
                case Print -> System.out.print(mem[memptr]);
                case Write -> mem[memptr] = cur.payload();
                case Open -> {
                    if((int)mem[memptr]==0){
                        ptr = jmpTable[ptr];
                    }
                }
                case Close -> {
                    ptr = jmpTable[ptr]-1;
                }
            }
            ptr++;
        }
    }
}
