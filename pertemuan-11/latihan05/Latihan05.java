import java.util.ArrayList;
import java.util.List;

class BarisDataException extends Exception {
    private final int nomorBaris;
    private final String alasan;

    public BarisDataException(int nomorBaris, String alasan) {
        super("Baris " + nomorBaris + ": " + alasan);
        this.nomorBaris = nomorBaris;
        this.alasan = alasan;
    }

    public BarisDataException(int nomorBaris, String alasan, Throwable penyebab) {
        super("Baris " + nomorBaris + ": " + alasan, penyebab);
        this.nomorBaris = nomorBaris;
        this.alasan = alasan;
    }

    public int getNomorBaris() { return nomorBaris; }
    public String getAlasan() { return alasan; }
}

class Pendaftar {
    final String nama;
    final int umur;

    Pendaftar(String nama, int umur) {
        this.nama = nama;
        this.umur = umur;
    }

    @Override
    public String toString() { return nama + " (" + umur + " tahun)"; }
}

public class Latihan05 {
    static Pendaftar bacaPendaftar(int nomorBaris, String teks) throws BarisDataException {
        // limit -1 agar kolom kosong di akhir (mis. "Ayu;") tetap dihitung
        String[] kolom = teks.split(";", -1);
        if (kolom.length != 2) {
            throw new BarisDataException(nomorBaris, "format harus nama;umur");
        }

        String nama = kolom[0].trim();
        if (nama.isEmpty()) {
            throw new BarisDataException(nomorBaris, "nama tidak boleh kosong");
        }

        String teksUmur = kolom[1].trim();
        int umur;
        try {
            umur = Integer.parseInt(teksUmur);
        } catch (NumberFormatException e) {
            throw new BarisDataException(nomorBaris, "umur '" + teksUmur + "' bukan angka", e);
        }

        if (umur < 17 || umur > 25) {
            throw new BarisDataException(nomorBaris, "umur " + umur + " di luar syarat 17-25 tahun");
        }
        return new Pendaftar(nama, umur);
    }

    public static void main(String[] args) {
        String[] data = {
            "Ayu Lestari;20", "Bima Saputra", "Citra Dewi;dua puluh", ";19",
            "Dimas Pratama;30", "Eka Putri;18", "Fajar Nugraha;21;Samarinda"
        };
        List<Pendaftar> diterima = new ArrayList<>();
        int ditolak = 0;

        System.out.println("=== IMPOR DATA PENDAFTAR LOMBA DEBAT ===");
        for (int i = 0; i < data.length; i++) {
            int nomor = i + 1;
            try {
                Pendaftar p = bacaPendaftar(nomor, data[i]);
                diterima.add(p);
                System.out.println("[OK] Baris " + nomor + ": " + p);
            } catch (BarisDataException e) {
                ditolak++;
                System.out.println("[DITOLAK] " + e.getMessage());
                if (e.getCause() != null) {
                    System.out.println("          (penyebab asli: kesalahan konversi angka)");
                }
            }
        }

        System.out.println("\nDiterima: " + diterima.size() + ", ditolak: " + ditolak);
        System.out.println("Daftar peserta:");
        for (Pendaftar p : diterima) {
            System.out.println("- " + p);
        }
    }
} 
    

