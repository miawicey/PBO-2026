import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class MesinException extends Exception {
    public MesinException(String pesan) { super(pesan); }
}

class ProdukTidakAdaException extends MesinException {
    private final String kode;

    public ProdukTidakAdaException(String kode) {
        super("Produk dengan kode " + kode + " tidak ada");
        this.kode = kode;
    }
    public String getKode() { return kode; }
}

class StokHabisException extends MesinException {
    private final String namaProduk;

    public StokHabisException(String namaProduk) {
        super("Stok " + namaProduk + " habis");
        this.namaProduk = namaProduk;
    }
    public String getNamaProduk() { return namaProduk; }
}

class UangKurangException extends MesinException {
    private final int kekurangan;

    public UangKurangException(int kekurangan) {
        super("Uang kurang " + kekurangan);
        this.kekurangan = kekurangan;
    }
    public int getKekurangan() { return kekurangan; }
}

class Produk {
    final String kode;
    final String nama;
    final int harga;
    int stok;

    Produk(String kode, String nama, int harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }
}

class MesinMinuman {
    private final List<Produk> daftarProduk = new ArrayList<>();
    private int totalPendapatan = 0;

    void tambahProduk(Produk p) { daftarProduk.add(p); }

    private Produk cariProduk(String kode) {
        for (Produk p : daftarProduk) {
            if (p.kode.equals(kode)) return p;
        }
        return null;
    }

    
    int beli(String kode, int uang) throws MesinException {
        Produk produk = cariProduk(kode);
        if (produk == null) {
            throw new ProdukTidakAdaException(kode);
        }
        if (produk.stok == 0) {
            throw new StokHabisException(produk.nama);
        }
        if (uang < produk.harga) {
            throw new UangKurangException(produk.harga - uang);
        }

        produk.stok--;
        totalPendapatan += produk.harga;
        return uang - produk.harga;
    }

    int getTotalPendapatan() { return totalPendapatan; }

    void cetakStok() {
        for (Produk p : daftarProduk) {
            System.out.println("- " + p.nama + " (" + p.kode + "): " + p.stok);
        }
    }
}

public class Mesin_Minuman {
    static String rupiah(int angka) {
        return "Rp" + String.format(Locale.US, "%,d", angka).replace(',', '.');
    }

    public static void main(String[] args) {
        MesinMinuman mesin = new MesinMinuman();
        mesin.tambahProduk(new Produk("A1", "Air Mineral", 3000, 5));
        mesin.tambahProduk(new Produk("A2", "Teh Botol", 5000, 3));
        mesin.tambahProduk(new Produk("A3", "Kopi Kaleng", 8000, 1)); // stok kecil
        mesin.tambahProduk(new Produk("A4", "Jus Jeruk", 10000, 2));

        String[] kodeTransaksi = {"A1", "A3", "A3", "A2", "Z9", "A4", "A2"};
        int[] uangTransaksi = {5000, 10000, 10000, 3000, 5000, 10000, 5000};

        System.out.println("=== MESIN MINUMAN FAKULTAS ===");
        for (int i = 0; i < kodeTransaksi.length; i++) {
            String kode = kodeTransaksi[i];
            int uang = uangTransaksi[i];
            System.out.println("\nTransaksi " + (i + 1) + ": kode " + kode + ", uang " + rupiah(uang));
            try {
                int kembalian = mesin.beli(kode, uang);
                System.out.println("  Berhasil! Kembalian " + rupiah(kembalian));
            } catch (ProdukTidakAdaException e) {
                System.out.println("  Maaf, kode " + e.getKode() + " tidak ditemukan. Cek lagi kodenya ya.");
            } catch (StokHabisException e) {
                System.out.println("  Maaf, " + e.getNamaProduk() + " sedang habis. Coba minuman lain.");
            } catch (UangKurangException e) {
                System.out.println("  Uang kurang " + rupiah(e.getKekurangan()) + ". Tambahkan uangmu.");
            } catch (MesinException e) {
                System.out.println("  Terjadi kesalahan mesin: " + e.getMessage());
            } finally {
                System.out.println("  Mesin siap melayani pembeli berikutnya");
            }
        }

        System.out.println("\nTotal pendapatan: " + rupiah(mesin.getTotalPendapatan()));
        System.out.println("Sisa stok:");
        mesin.cetakStok();
    }
}