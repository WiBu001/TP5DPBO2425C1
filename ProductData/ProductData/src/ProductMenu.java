import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductMenu extends JFrame {
    public static void main(String[] args) {
        // buat object window
        ProductMenu menu  = new ProductMenu();

        // atur ukuran window
        menu.setSize(700, 600);

        // letakkan window di tengah layar
        menu.setLocationRelativeTo(null);

        // isi window
        menu.setContentPane(menu.mainPanel);

        // ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);

        // tampilkan window
        menu.setVisible(true);

        // agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // Deklarasi Database
    private Database database;

    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField hargaField;
    private JTable productTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> kategoriComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel namaLabel;
    private JLabel hargaLabel;
    private JLabel kategoriLabel;
    private JRadioButton baruRadioButton;
    private JRadioButton bekasRadioButton;
    private ButtonGroup kondisiGroup;

    // constructor
    public ProductMenu() {

        // Construct Database
        database = new Database();

        // Kelompokkan Radio Button
        kondisiGroup = new ButtonGroup();
        kondisiGroup.add(baruRadioButton);
        kondisiGroup.add(bekasRadioButton);

        // isi tabel produk
        productTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] KategoriData = {"???", "Elektronik", "Makanan", "Minuman", "Pakaian", "Alat Tulis"};
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(KategoriData));

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedIndex == -1){
                    insertData();
                }else{
                    updateData();
                }
            }
        });
        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: tambahkan konfirmasi sebelum menghapus data
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Apakah kamu yakin ingin menghapus data ini?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    deleteData();
                }
            }
        });
        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = productTable.getSelectedRow();

                // simpan value textfield dan combo box
                String curId = productTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curName = productTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curPrice = productTable.getModel().getValueAt(selectedIndex, 3).toString();
                String curKategori = productTable.getModel().getValueAt(selectedIndex, 4).toString();
                String curKondisi = productTable.getModel().getValueAt(selectedIndex, 5).toString();

                if (curKondisi.equalsIgnoreCase("Baru")) {
                    baruRadioButton.setSelected(true);
                } else {
                    bekasRadioButton.setSelected(true);
                }

                // ubah isi textfield dan combo box
                idField.setText(curId);
                namaField.setText(curName);
                hargaField.setText(curPrice);
                kategoriComboBox.setSelectedItem(curKategori);

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText(("Update"));

                // tampilkan button delete
                deleteButton.setVisible(true);

            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] cols = {"No", "ID Product", "Nama", "Harga", "Kategori", "Kondisi"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        try{
            ResultSet resultSet = database.selectQuery("SELECT * FROM product");

            int i = 0;
            while(resultSet.next()) {
                Object[] row = new Object[6];
                row[0] = i + 1;
                row[1] = resultSet.getString("id");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("harga");
                row[4] = resultSet.getString("kategori");
                row[5] = resultSet.getString("kondisi");
                tmp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tmp; // return juga harus diganti
    }

    public void insertData() {
        try {
            // ambil value dari textfield dan combobox
            String id = idField.getText();
            String nama = namaField.getText();
            String hargaText = hargaField.getText();
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String kondisi = baruRadioButton.isSelected() ? "Baru" : "Bekas";

            // Jika Field ada yang kosong
            if (id.isEmpty() || nama.isEmpty() || kategori.isEmpty() || kondisi.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua Kolom Harus Diisi!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ubah field harga menjadi angka
            double harga = Double.parseDouble(hargaField.getText());

            // Cek ID yang sama
            try {
                ResultSet resultSet = database.selectQuery("SELECT * FROM product WHERE id = '" + id + "'");
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "ID Sudah Digunakan!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat memeriksa ID!\n" + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //tambahkan data ke db
            String sqlQuery = "INSERT INTO product VALUES ('" + id + "', '" + nama + "', " + harga + ", '" + kategori + "', '" + kondisi + "')";
            database.insertUpdateDeleteQuery(sqlQuery);

            // update tabel
            productTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Insert Berhasil");
            JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan");
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Harga Harus Berupa Angka", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void updateData() {
        try {
            // ambil value dari textfield dan combobox
            String id = idField.getText();
            String nama = namaField.getText();
            String hargaText = hargaField.getText();
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String kondisi = baruRadioButton.isSelected() ? "Baru" : "Bekas";

            // Jika Field Ada Yang Kosong
            if (id.isEmpty() || nama.isEmpty() || hargaText.isEmpty() || kategori.equals("???") || kondisi.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double harga = Double.parseDouble(hargaText);

            // update data di database
            String sqlQuery = "UPDATE product SET " + "nama = '" + nama + "', " + "harga = " + harga + ", " + "kategori = '" + kategori + "', " + "kondisi = '" + kondisi + "' " + "WHERE id = '" + id + "'";
            database.insertUpdateDeleteQuery(sqlQuery);

            // update tabel
            productTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Update Berhasil");
            JOptionPane.showMessageDialog(null, "Data Berhasil Diupdate");
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Harga Harus Berupa Angka", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData() {

        // ambil id dari field
        String id = idField.getText();

        // hapus data dari database
        String sqlQuery = "DELETE FROM product WHERE id = '" + id + "'";
        database.insertUpdateDeleteQuery(sqlQuery);

        // update tabel
        productTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete Berhasil");
        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");

    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        idField.setText("");
        namaField.setText("");
        hargaField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        kondisiGroup.clearSelection();


        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }
}