public class Product {
    private String id;
    private String nama;
    private double harga;
    private String kategori;
    private String kondisi;

    public Product(String id, String nama, double harga, String kategori, String kondisi) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
        this.kondisi = kondisi;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public  void setKondisi(String kondisi) {this.kondisi = kondisi;}

    public String getId() {
        return this.id;
    }

    public String getNama() {
        return this.nama;
    }

    public double getHarga() {
        return this.harga;
    }

    public String getKategori() {
        return this.kategori;
    }

    public  String getKondisi() {return this.kondisi;}
}