import java.util.ArrayList;
import java.util.List;

class PerpustakaanException extends Exception {
    public PerpustakaanException(String pesan) { super(pesan); }
}

class BukuSedangDipinjamException extends PerpustakaanException {
    private final String judul;
    private final String peminjam;

    public BukuSedangDipinjamException(String judul, String peminjam) {
        super("Buku '" + judul + "' sedang dipinjam oleh " + peminjam);
        this.judul = judul;
        this.peminjam = peminjam;
    }
    public String getJudul() { return judul; }
    public String getPeminjam() { return peminjam; }
}

class BatasPinjamException extends PerpustakaanException {
    private final String nama;
    private final int batas;

    public BatasPinjamException(String nama, int batas) {
        super(nama + " sudah meminjam " + batas + " buku (batas maksimal)");
        this.nama = nama;
        this.batas = batas;
    }
    public String getNama() { return nama; }
    public int getBatas() { return batas; }
}

class Buku {
    final String judul;
    String peminjam; 

    Buku(String judul) { this.judul = judul; }
}

class Anggota {
    static final int BATAS_PINJAM = 2;
    final String nama;
    private final List<Buku> dipinjam = new ArrayList<>();

    Anggota(String nama) { this.nama = nama; }

    void pinjam(Buku buku) throws BukuSedangDipinjamException, BatasPinjamException {
        if (buku.peminjam != null) {
            throw new BukuSedangDipinjamException(buku.judul, buku.peminjam);
        }
        if (dipinjam.size() >= BATAS_PINJAM) {
            throw new BatasPinjamException(nama, BATAS_PINJAM);
        }
        buku.peminjam = nama;
        dipinjam.add(buku);
    }

    void kembalikan(Buku buku) throws PerpustakaanException {
        if (!dipinjam.contains(buku)) {
            throw new PerpustakaanException(nama + " tidak sedang meminjam '" + buku.judul + "'");
        }
        buku.peminjam = null;
        dipinjam.remove(buku);
    }
}

public class latihan03 {
    static void cobaPinjam(Anggota anggota, Buku buku) {
        try {
            anggota.pinjam(buku);
            System.out.println(anggota.nama + " meminjam '" + buku.judul + "'");
        } catch (BatasPinjamException e) {
            System.out.println("Gagal: " + e.getMessage());
            System.out.println("  Saran: kembalikan dulu salah satu dari " + e.getBatas() + " buku");
        } catch (BukuSedangDipinjamException e) {
            System.out.println("Gagal: " + e.getMessage());
            System.out.println("  Saran: tunggu sampai " + e.getPeminjam() + " mengembalikannya");
        }
    }

    public static void main(String[] args) {
        Anggota ayu = new Anggota("Ayu");
        Anggota bima = new Anggota("Bima");
        Buku laskar = new Buku("Laskar Pelangi");
        Buku bumi = new Buku("Bumi Manusia");
        Buku negeri = new Buku("Negeri 5 Menara");

        System.out.println("=== PEMINJAMAN BUKU ===");
        cobaPinjam(ayu, laskar);
        cobaPinjam(ayu, bumi);
        cobaPinjam(ayu, negeri);
        cobaPinjam(bima, laskar);

        System.out.println("\n=== PENGEMBALIAN ===");
        try {
            bima.kembalikan(bumi);
        } catch (PerpustakaanException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
        try {
            ayu.kembalikan(laskar);
            System.out.println("Ayu mengembalikan 'Laskar Pelangi'");
        } catch (PerpustakaanException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
        cobaPinjam(bima, laskar);
    }
}