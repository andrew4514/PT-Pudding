package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Random;
import java.util.ArrayList;
import java.util.Optional;



public class Main extends Application {
	private ArrayList<MenuData> daftarMenu = new ArrayList<>();
    
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("PT Pudding Database");

        // VBox
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        // Judul
        Label titleLabel = new Label("PT Pudding Database");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        MenuBar menuBar = new MenuBar();

        // Insert
        Button insertButton = new Button("Insert Menu Baru");
        insertButton.setOnAction(e -> showInsertMenuDialog());

        // View
        Button viewButton = new Button("View Menu");
        viewButton.setOnAction(e -> showViewMenuDialog());
        
        // Update 
        Button updateButton = new Button("Update Menu");
        updateButton.setOnAction(e -> {
            if (daftarMenu.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Tidak ada menu yang tersedia untuk diperbarui.");
                alert.showAndWait();
            } else {
            	showViewMenuDialog();
                ChoiceDialog<MenuData> dialog = new ChoiceDialog<>(null, daftarMenu);
                dialog.setTitle("Pilih Menu");
                dialog.setHeaderText("Pilih menu yang akan diperbarui:");
                dialog.setContentText("Menu:");

                Optional<MenuData> result = dialog.showAndWait();

                result.ifPresent(selectedMenu -> showUpdateMenuDialog(selectedMenu));
            }
        });
        
        
        Button deleteButton = new Button("Delete Menu");
        deleteButton.setOnAction(e -> {
        	
            if (daftarMenu.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Tidak ada menu yang tersedia untuk dihapus.");
                alert.showAndWait();
            } else {
            	showViewMenuDialog();
                ChoiceDialog<MenuData> dialog = new ChoiceDialog<>(null, daftarMenu);
                dialog.setTitle("Pilih Menu");
                dialog.setHeaderText("Pilih menu yang akan dihapus:");
                dialog.setContentText("Menu:");

                Optional<MenuData> result = dialog.showAndWait();

                result.ifPresent(selectedMenu -> showDeleteMenuDialog(selectedMenu));
            }
        });
        
        mainLayout.getChildren().addAll(titleLabel, menuBar, insertButton, viewButton, updateButton, deleteButton);
        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
	}

	
	private void showInsertMenuDialog() {
        Stage insertStage = new Stage();
        insertStage.setTitle("Insert New Menu");

        VBox insertLayout = new VBox(10);
        insertLayout.setPadding(new Insets(10));

        Label kodeLabel = new Label("Kode Menu:");
        Label kodeValueLabel = new Label(generateRandomKode());
        Label namaLabel = new Label("Nama Menu:");
        TextField namaField = new TextField();
        Label hargaLabel = new Label("Harga Menu:");
        TextField hargaField = new TextField();
        Label stokLabel = new Label("Stok Menu:");
        TextField stokField = new TextField();

        Button insertButton = new Button("Insert");
        insertButton.setOnAction(e -> {
            String kodeMenu = kodeValueLabel.getText();
            String namaMenu = namaField.getText();
            double hargaMenu = Double.parseDouble(hargaField.getText());
            int stokMenu = Integer.parseInt(stokField.getText());
            MenuData menu = new MenuData(kodeMenu, namaMenu, hargaMenu, stokMenu);
            daftarMenu.add(menu);
            System.out.println("Menu baru berhasil disimpan!");
            insertStage.close();
        });

        insertLayout.getChildren().addAll(kodeLabel, kodeValueLabel, namaLabel, namaField, hargaLabel, hargaField, stokLabel, stokField, insertButton);

        Scene insertScene = new Scene(insertLayout, 300, 300);
        insertStage.setScene(insertScene);
        insertStage.show();
    }
	
	

	@SuppressWarnings("unchecked")
	private void showViewMenuDialog() {
	    Stage viewStage = new Stage();
	    viewStage.setTitle("View Menu");

	    VBox viewLayout = new VBox(10);
	    viewLayout.setPadding(new Insets(10));
	    
	    TableView<MenuData> tableView = new TableView<>();

	    TableColumn<MenuData, String> kodeColumn = new TableColumn<>("Kode Menu");
	    kodeColumn.setCellValueFactory(new PropertyValueFactory<>("kodeMenu")); 
	    TableColumn<MenuData, String> namaColumn = new TableColumn<>("Nama Menu");
	    namaColumn.setCellValueFactory(new PropertyValueFactory<>("namaMenu")); 
	    TableColumn<MenuData, Double> hargaColumn = new TableColumn<>("Harga Menu");
	    hargaColumn.setCellValueFactory(new PropertyValueFactory<>("hargaMenu")); 
	    TableColumn<MenuData, Integer> stokColumn = new TableColumn<>("Stok Menu");
	    stokColumn.setCellValueFactory(new PropertyValueFactory<>("stokMenu")); 
	    
	    tableView.getColumns().addAll(kodeColumn, namaColumn, hargaColumn, stokColumn);
	    tableView.getItems().addAll(daftarMenu);

	    viewLayout.getChildren().add(tableView);

	    Scene viewScene = new Scene(viewLayout, 400, 300);
	    viewStage.setScene(viewScene);
	    viewStage.show();
	}

	
	private void showUpdateMenuDialog(MenuData selectedMenu) {
	    Stage updateStage = new Stage();
	    updateStage.setTitle("Update Menu");

	    VBox updateLayout = new VBox(10);
	    updateLayout.setPadding(new Insets(10));

	    if (selectedMenu != null) {
	        Label kodeLabel = new Label("Kode Menu:");
	        Label kodeValueLabel = new Label(selectedMenu.getKodeMenu());
	        Label namaLabel = new Label("Nama Menu:");
	        Label namaValueLabel = new Label(selectedMenu.getNamaMenu());
	        Label hargaLabel = new Label("Harga Menu:");
	        TextField hargaField = new TextField(Double.toString(selectedMenu.getHargaMenu()));
	        Label stokLabel = new Label("Stok Menu:");
	        TextField stokField = new TextField(Integer.toString(selectedMenu.getStokMenu()));

	        Button updateButton = new Button("Update");
	        updateButton.setOnAction(e -> {
	            double newHarga = Double.parseDouble(hargaField.getText());
	            int newStok = Integer.parseInt(stokField.getText());
	            selectedMenu.setHargaMenu(newHarga);
	            selectedMenu.setStokMenu(newStok);
	            System.out.println("Menu berhasil diperbarui!");
	            updateStage.close();
	        });

	        updateLayout.getChildren().addAll(kodeLabel, kodeValueLabel, namaLabel, namaValueLabel, hargaLabel, hargaField, stokLabel, stokField, updateButton);
	    } else {
	        Label errorMessageLabel = new Label("Menu yang dipilih tidak tersedia.");
	        updateLayout.getChildren().add(errorMessageLabel);
	    }

	    Scene updateScene = new Scene(updateLayout, 300, 300);
	    updateStage.setScene(updateScene);
	    updateStage.show();
	}
        
	
	
	private void showDeleteMenuDialog(MenuData selectedMenu) {
		daftarMenu.remove(selectedMenu);
		System.out.println("Menu berhasil dihapus.");
	}
	
		
	private String generateRandomKode() {
        Random random = new Random();
        int randomNumber = random.nextInt(900) + 100;
        return "PD-" + randomNumber;
    }

	
	
	
	public static void main(String[] args) {
		launch(args);
	}

}


