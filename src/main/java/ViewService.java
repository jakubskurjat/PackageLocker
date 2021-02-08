import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class ViewService {

    private static Session session = LaunchWindowController.getFactory().openSession();

    public static void preparingTableViewForClients(String queryView, TableView<PackagesView> packagesTable, TableColumn<?, ?> idCol, TableColumn<?, ?> sizeCol, TableColumn<?, ?> shipmentDateCol, TableColumn<?, ?> collectionDateCol,
                                                    TableColumn<?, ?> priceCol, TableColumn<?, ?> senderCol, TableColumn<?, ?> receiverCol, TableColumn<?, ?> senderLockerCol, TableColumn<?, ?> receiverLockerCol) {
        EntityManagerFactory emf = session.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Query query = em.createNativeQuery(queryView, PackagesView.class);

        preparingTable(packagesTable, idCol, sizeCol, shipmentDateCol, collectionDateCol, priceCol, senderCol, receiverCol, senderLockerCol, receiverLockerCol, query);
    }

    public static void preparingTableViewForStaffers(String queryView, TableView<PackagesView> packagesTable, TableColumn<?, ?> idCol, TableColumn<?, ?> sizeCol, TableColumn<?, ?> shipmentDateCol, TableColumn<?, ?> collectionDateCol,
                                                     TableColumn<?, ?> priceCol, TableColumn<?, ?> senderCol, TableColumn<?, ?> receiverCol, TableColumn<?, ?> senderLockerCol, TableColumn<?, ?> receiverLockerCol, TableColumn<?, ?> idLockerColS) {
        EntityManagerFactory emf = session.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Query query = em.createNativeQuery(queryView, CompanyView.class);

        preparingTable(packagesTable, idCol, sizeCol, shipmentDateCol, collectionDateCol, priceCol, senderCol, receiverCol, senderLockerCol, receiverLockerCol, query);

        idLockerColS.setCellValueFactory(
                new PropertyValueFactory<>("locker")
        );
    }

    public static void preparingTableViewForPackageLocker(String queryView, TableView<PackagesView> packagesTable, TableColumn<?, ?> idCol, TableColumn<?, ?> isEmpty) {
        EntityManagerFactory emf = session.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Query query = em.createNativeQuery(queryView, PackageLockerView.class);

        List<PackagesView> list = query.getResultList();

        ObservableList<PackagesView> packagesList = FXCollections.observableArrayList(list);

        packagesTable.setItems(packagesList);

        idCol.setCellValueFactory(
                new PropertyValueFactory<>("idLocker")
        );

        isEmpty.setCellValueFactory(
                new PropertyValueFactory<>("isEmpty")
        );
    }

    private static void preparingTable(TableView<PackagesView> packagesTable, TableColumn<?, ?> idCol, TableColumn<?, ?> sizeCol, TableColumn<?, ?> shipmentDateCol, TableColumn<?, ?> collectionDateCol, TableColumn<?, ?> priceCol, TableColumn<?, ?> senderCol, TableColumn<?, ?> receiverCol, TableColumn<?, ?> senderLockerCol, TableColumn<?, ?> receiverLockerCol, Query query) {
        List<PackagesView> list = query.getResultList();

        ObservableList<PackagesView> packagesList = FXCollections.observableArrayList(list);

        packagesTable.setItems(packagesList);


        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );
        sizeCol.setCellValueFactory(
                new PropertyValueFactory<>("size")
        );
        shipmentDateCol.setCellValueFactory(
                new PropertyValueFactory<>("shipmentDate")
        );
        collectionDateCol.setCellValueFactory(
                new PropertyValueFactory<>("collectionDate")
        );
        priceCol.setCellValueFactory(
                new PropertyValueFactory<>("price")
        );
        senderCol.setCellValueFactory(
                new PropertyValueFactory<>("sender")
        );
        receiverCol.setCellValueFactory(
                new PropertyValueFactory<>("receiver")
        );
        senderLockerCol.setCellValueFactory(
                new PropertyValueFactory<>("packageLockerAddressSender")
        );
        receiverLockerCol.setCellValueFactory(
                new PropertyValueFactory<>("packageLockerAddressReceiver")
        );
    }
}
