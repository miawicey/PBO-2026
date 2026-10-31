import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class KedaiException extends Exception {
    public KedaiException(String pesan) { super(pesan); }
}

class MenuSudahAdaException extends KedaiException {
    private final String kode;
    private final String namaLama;

    public MenuSudahAdaException(String kode, String namaLama) {
        super("Kode " + kode + " sudah dipakai oleh " + namaLama);
        this.kode = kode;
        this.namaLama = namaLama;
    }
    public String getKode() { return kode; }
    public String getNamaLama() { return namaLama; }
}

class Menu {
    final String kode;
    final String nama;
    final int harga;

    Menu(String kode, String nama, int harga) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
    }

    static String rupiah(int angka) {
        return "Rp" + String.format(Locale.US, "%,d", angka).replace(',', '.');
    }

    @Override
    public String toString() {
        return "[" + kode + "] " + nama + " - " + rupiah(harga);
    }
}

class Kedai {
    private final List<Menu> daftarMenu = new ArrayList<>();

    void tambahMenu(Menu menu) throws MenuSudahAdaException {
        for (Menu m : daftarMenu) {
            if (m.kode.equals(menu.kode)) {
                throw new MenuSudahAdaException(menu.kode, m.nama);
            }
        }
        daftarMenu.add(menu);
    }

    Menu cariMenu(String kode) {
        for (Menu m : daftarMenu) {
            if (m.kode.equals(kode)) return m;
        }
        return null;
    }

    int jumlahMenu() { return daftarMenu.size(); }
}

public class Latihan04 {
    public static void main(String[] args) {
        Kedai kedai = new Kedai();
        Menu[] calon = {
            new Menu("M01", "Kopi Susu Gula Aren", 18000),
            new Menu("M02", "Es Teh Manis", 8000),
            new Menu("M03", "Nasi Goreng Kampung", 25000),
            new Menu("M04", "Mie Goreng Jawa", 23000),
            new Menu("M05", "Pisang Goreng Keju", 15000),
            new Menu("M02", "Es Jeruk", 10000),
            new Menu("M06", "Teh Tarik", 12000),
            new Menu("M06", "Kopi Tubruk", 12000),
        };

        System.out.println("=== MENAMBAH MENU ===");
        for (Menu menu : calon) {
            try {
                kedai.tambahMenu(menu);
                System.out.println("[OK] " + menu);
            } catch (MenuSudahAdaException e) {
                System.out.println("[GAGAL] " + e.getMessage());
                System.out.println("        " + menu.nama + " tidak ditambahkan, pilih kode selain " + e.getKode());
            }
        }
        System.out.println("Jumlah menu terdaftar: " + kedai.jumlahMenu());
    }
} {
    
}
