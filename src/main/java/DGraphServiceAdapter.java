import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import io.dgraph.DgraphProto;
import io.dgraph.Transaction;
import model.Carriage;
import model.Carriages;

import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

public class DGraphServiceAdapter implements DGraphRepositoryPort {
    @Override
    public Transaction save(Transaction txn) {
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();

        System.out.println("Podaj [id]:");
        String id = scanner.nextLine();

        System.out.println("Podaj [nazwe przychodni]:");
        String name = scanner.nextLine();

        System.out.println("Podaj [skad jedziesz]:");
        String from = scanner.nextLine();

        System.out.println("Podaj [dokad jedziesz]:");
        String to = scanner.nextLine();

        System.out.println("Podaj [dystans]:");
        int distance = scanner.nextInt();

        try {
            Carriage p = new Carriage(id, name, from, to, distance);

            String json = gson.toJson(p);
            DgraphProto.Mutation mutation =
                    DgraphProto.Mutation.newBuilder().setSetJson(ByteString.copyFromUtf8(json.toString())).build();

            txn.mutate(mutation);
            txn.commit();


        } finally {
            txn.discard();
        }
        return txn;
    }

    @Override
    public Transaction deleteById(Transaction txn) {
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();
        System.out.println("Podaj [id]:");
        String id = scanner.nextLine();
        String query =
                "query delete($a: string){\n" + "delete(func: eq(id, $a)) {\n" + "    id\n" + "  }\n" + "}";
        Map<String, String> vars = Collections.singletonMap("$a", id);
        DgraphProto.Response res = txn.queryWithVars(query, vars);

        Carriages ppl = gson.fromJson(res.getJson().toStringUtf8(), Carriages.class);
        System.out.printf("people found: %d\n", ppl.all.size());
        ppl.all.forEach(clinic -> System.out.println(clinic.name));

        for (Carriage carriage : ppl.all) {
            String json = gson.toJson(carriage);
            DgraphProto.Mutation mutation =
                    DgraphProto.Mutation.newBuilder().setDeleteJson(ByteString.copyFromUtf8(json.toString())).build();
            txn.mutate(mutation);
        }
        txn.commit();
        return txn;
    }

    @Override
    public Transaction updateById(Transaction txn) {
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();
        System.out.println("Podaj [id]:");
        String id = scanner.nextLine();
        String query =
                "query delete($a: string){\n" + "delete(func: eq(id, $a)) {\n" + "    id\n" + "  }\n" + "}";
        Map<String, String> vars = Collections.singletonMap("$a", id);
        DgraphProto.Response res = txn.queryWithVars(query, vars);

        Carriages ppl = gson.fromJson(res.getJson().toStringUtf8(), Carriages.class);
        for (Carriage carriage : ppl.all) {
            String json = gson.toJson(carriage);
            DgraphProto.Mutation mutation =
                    DgraphProto.Mutation.newBuilder().setDeleteJson(ByteString.copyFromUtf8(json.toString())).build();
            txn.mutate(mutation);
        }

        System.out.println("Podaj [id]:");
        String newId = scanner.nextLine();

        System.out.println("Podaj [nazwe przychodni]:");
        String newName = scanner.nextLine();

        System.out.println("Podaj [skad jedziesz]:");
        String newFrom = scanner.nextLine();

        System.out.println("Podaj [dokad jedziesz]:");
        String newTo = scanner.nextLine();

        System.out.println("Podaj [dystans]:");
        int newDistance = scanner.nextInt();

        try {
            Carriage p = new Carriage(newId, newName, newFrom, newTo, newDistance);
            String json = gson.toJson(p);
            DgraphProto.Mutation mutation =
                    DgraphProto.Mutation.newBuilder().setSetJson(ByteString.copyFromUtf8(json.toString())).build();
            txn.mutate(mutation);
        } finally {
            txn.discard();
        }

        txn.commit();
        return txn;
    }

    @Override
    public void getByName(Transaction txn) {
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();
        System.out.println("Podaj [name]:");
        String name = scanner.nextLine();
        String query =
                "query delete($a: string){\n" + "delete(func: eq(name, $a)) {\n" + "    name\n" + "  }\n" + "}";
        Map<String, String> vars = Collections.singletonMap("$a", name);
        DgraphProto.Response res = txn.queryWithVars(query, vars);

        Carriages ppl = gson.fromJson(res.getJson().toStringUtf8(), Carriages.class);

        for (Carriage carriage : ppl.all) {
            System.out.println(carriage);
        }
    }

    @Override
    public void getById(Transaction txn) {
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();
        System.out.println("Podaj [id]:");
        String id = scanner.nextLine();
        String query =
                "query all($a: string){\n" + "all(func: eq(id, $a)) {\n" + "    id\n" + "  }\n" + "}";
        Map<String, String> vars = Collections.singletonMap("$a", id);
        DgraphProto.Response res = txn.queryWithVars(query, vars);

        Carriages ppl = gson.fromJson(res.getJson().toStringUtf8(), Carriages.class);

        for (Carriage carriage : ppl.all) {
            System.out.println(carriage);
        }
    }

    @Override
    public void findAverageDistanceByName(Transaction txn) {
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();
        System.out.println("Podaj [name]:");
        String name = scanner.nextLine();

        String query =
                "query delete($a: string){\n" + "delete(func: eq(name, $a)) {\n" + "    name\n" + "  }\n" + "}";
        Map<String, String> vars = Collections.singletonMap("$a", name);
        DgraphProto.Response res = txn.queryWithVars(query, vars);

        Carriages ppl = gson.fromJson(res.getJson().toStringUtf8(), Carriages.class);

        int sum = 0;
        for (Carriage carriage : ppl.all) {
            sum += carriage.traveledDistance;
        }

        System.out.println(sum / ppl.all.size());
    }
}
