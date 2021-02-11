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

/**
 * This class represents a site that contains all views the user can view.
 */
public class ViewService {

    /**
     * This private static field represents <code>Session</code>.
     */
    private static Session session = LaunchWindowController.getFactory().openSession();

    /**
     * This method prepares a view table for the client. It is used when viewing packages received,
     * to receive and shipped.
     *
     * @param queryView         represents the query on the basis of which the view is built.
     * @param packagesTable     represents the package table.
     * @param idCol             represents the id of the displayed package in the table.
     * @param sizeCol           represents the size of displayed package in the table.
     * @param shipmentDateCol   represents the shipment of displayed package in the table.
     * @param collectionDateCol represents the collection date of displayed package in the table.
     * @param priceCol          represents the price of displayed package in the table.
     * @param senderCol         represents the sender of displayed package in the table.
     * @param receiverCol       represents the receiver of displayed package in the table.
     * @param senderLockerCol   represents the package locker from which the package was sent, displayed in the table.
     * @param receiverLockerCol represents the package locker from which another user can receive a package displayed in the table.
     */
    public static void preparingTableViewForClients(String queryView, TableView<PackagesView> packagesTable, TableColumn<?, ?> idCol, TableColumn<?, ?> sizeCol, TableColumn<?, ?> shipmentDateCol, TableColumn<?, ?> collectionDateCol,
                                                    TableColumn<?, ?> priceCol, TableColumn<?, ?> senderCol, TableColumn<?, ?> receiverCol, TableColumn<?, ?> senderLockerCol, TableColumn<?, ?> receiverLockerCol) {
        EntityManagerFactory emf = session.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Query query = em.createNativeQuery(queryView, PackagesView.class);

        preparingTable(packagesTable, idCol, sizeCol, shipmentDateCol, collectionDateCol, priceCol, senderCol, receiverCol, senderLockerCol, receiverLockerCol, query);
    }

    /**
     * This method prepares a view table for the staffer. It is used when displaying data of all packages
     * that are or have been in the package locker.
     *
     * @param queryView         represents the query on the basis of which the view is built.
     * @param packagesTable     represents the package table.
     * @param idCol             represents the id of the displayed package in the table.
     * @param sizeCol           represents the size of displayed package in the table.
     * @param shipmentDateCol   represents the shipment of displayed package in the table.
     * @param collectionDateCol represents the collection date of displayed package in the table.
     * @param priceCol          represents the price of displayed package in the table.
     * @param senderCol         represents the sender of displayed package in the table.
     * @param receiverCol       represents the receiver of displayed package in the table.
     * @param senderLockerCol   represents the package locker from which the package was sent, displayed in the table.
     * @param receiverLockerCol represents the package locker from which another user can receive a package displayed in the table.
     * @param idLockerColS      represents the locker in which the package is located or was.
     */
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


    /**
     * This method represents a list of all occupied lockers in a given package locker.
     * It is used when checking the status of a given package locker selected by a staffer.
     *
     * @param queryView   represents the query on the basis of which the view is built.
     * @param lockerTable represents the locker table.
     * @param idCol       represents the id of the displayed locker in the table.
     * @param isEmpty     represents the availability of displayed locker.
     */
    public static void preparingTableViewForPackageLocker(String queryView, TableView<PackageLockerView> lockerTable, TableColumn<?, ?> idCol, TableColumn<?, ?> isEmpty) {
        EntityManagerFactory emf = session.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Query query = em.createNativeQuery(queryView, PackageLockerView.class);

        List<PackageLockerView> list = query.getResultList();
        ObservableList<PackageLockerView> packagesList = FXCollections.observableArrayList(list);

        for (PackageLockerView p : packagesList) {
            if (p.getIsEmpty().equals("0")) {
                p.setIsEmpty("false");
            }
        }

        lockerTable.setItems(packagesList);

        idCol.setCellValueFactory(
                new PropertyValueFactory<>("idLocker")
        );

        isEmpty.setCellValueFactory(
                new PropertyValueFactory<>("isEmpty")
        );
    }

    /**
     * This method represents a list of addresses of all package lockers.
     * It is used when displaying the contents of a given parcel locker.
     * It presents the employee with a preview of the addresses of all package lockers,
     * thanks to which he can easily select the appropriate id of the package lockers,
     * the status of which he wants to view.
     *
     * @param addressesTable represents the  table containing addresses.
     * @param idCol          represents the id of the displayed package in the table.
     * @param addressesCol   represents the addresses of the displayed package lockers in the table.
     */
    public static void preparingTableViewForAddressesOfPackageLockers(TableView<AddressesView> addressesTable, TableColumn<?, ?> idCol, TableColumn<?, ?> addressesCol) {
        EntityManagerFactory emf = session.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        String queryView = "SELECT * FROM AddressesView";

        Query query = em.createNativeQuery(queryView, AddressesView.class);

        List<AddressesView> list = query.getResultList();

        ObservableList<AddressesView> packagesList = FXCollections.observableArrayList(list);

        addressesTable.setItems(packagesList);

        idCol.setCellValueFactory(
                new PropertyValueFactory<>("idPackageLocker")
        );

        addressesCol.setCellValueFactory(
                new PropertyValueFactory<>("addressPackageLocker")
        );
    }

    /**
     * This method pushes the relevant information from the package view into the appropriate columns.
     * It is called in the method <code>preparingTableViewForClients</code> and <code>preparingTableViewForStaffers</code>
     * methods.
     *
     * @param packagesTable     represents the package table.
     * @param idCol             represents the id of the displayed package in the table.
     * @param sizeCol           represents the size of displayed package in the table.
     * @param shipmentDateCol   represents the shipment of displayed package in the table.
     * @param collectionDateCol represents the collection date of displayed package in the table.
     * @param priceCol          represents the price of displayed package in the table.
     * @param senderCol         represents the sender of displayed package in the table.
     * @param receiverCol       represents the receiver of displayed package in the table.
     * @param senderLockerCol   represents the package locker from which the package was sent, displayed in the table.
     * @param receiverLockerCol represents the package locker from which another user can receive a package displayed in the table.
     * @param query             represents the list of all packages that the user can view.
     */
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
