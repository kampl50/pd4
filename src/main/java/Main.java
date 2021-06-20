import io.dgraph.DgraphClient;
import io.dgraph.DgraphProto.Operation;
import io.dgraph.Transaction;

import java.util.Scanner;

public class Main {

    public static void main(final String[] args) {
        DgraphClient dgraphClient = DGraphClient.createDgraphClient(false);
        dgraphClient.alter(Operation.newBuilder().setDropAll(true).build());
        String schema = "name: string @index(exact) .";
        Operation operation = Operation.newBuilder().setSchema(schema).build();
        dgraphClient.alter(operation);
        Transaction txn = dgraphClient.newTransaction();

        Scanner scanner = new Scanner(System.in);
        DGraphRepositoryPort repositoryPort = new DGraphServiceAdapter();
        int select;
        do {
            System.out.println("[0]wyjscie\t\t[1]dodaj przewoz\t\t[2]usun przewoz\t\t[3]znajdz po id\t\t[4]aktualizuj przewoz\t\t[5]znajdz po nazwie\t\t" +
                    "[6]sredni przebyty dystans po nazwie\\t\"");
            select = scanner.nextInt();
            switch (select) {
                case 0:
                    break;
                case 1:
                    repositoryPort.save(txn);
                    break;
                case 2:
                    repositoryPort.deleteById(txn);
                    break;
                case 3:
                    repositoryPort.getById(txn);
                    break;
                case 4:
                    repositoryPort.updateById(txn);
                    break;
                case 5:
                    repositoryPort.getByName(txn);
                    break;
                case 6:
                    repositoryPort.findAverageDistanceByName(txn);
                    break;
                default:
                    throw new IllegalStateException("Nie ma takiej opcji pod tym numerem: " + select);
            }
        } while (select != 0);

    }
}
