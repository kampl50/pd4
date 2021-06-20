import io.dgraph.Transaction;

public interface DGraphRepositoryPort {

    Transaction save(Transaction txn);

    Transaction deleteById(Transaction txn);

    Transaction updateById(Transaction txn);

    void getByName(Transaction txn);

    void getById(Transaction txn);

    void findAverageDistanceByName(Transaction txn);
}
