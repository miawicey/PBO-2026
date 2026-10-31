public class Latihan01 {
    private final String nama;
    private final String[] daftarTeks;

    public Latihan01(String nama, String[] daftarTeks) {
        this.nama = nama;
        this.daftarTeks = daftarTeks;
    }

    public int rataRataNilai() {
        int total = 0;
        int jumlah = 0;
        for (String teks : daftarTeks) {
            try {
                total += Integer.parseInt(teks);
                jumlah++;
            } catch (NumberFormatException e) {
                System.out.println("  '" + teks + "' bukan angka, dilewati");
            }
        }
        return Math.floorDiv(total, jumlah);
    }

    public void proses() {
        System.out.println("=== " + nama + " ===");
        try {
            System.out.println("Rata-rata: " + rataRataNilai());
        } catch (ArithmeticException e) {
            System.out.println("Tidak ada nilai yang valid, rata-rata tidak bisa dihitung");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Latihan01 kelasA = new Latihan01("KELAS A", new String[]{"80", "75", "sembilan puluh", "92", ""});
        Latihan01 kelasB = new Latihan01("KELAS B", new String[]{"-", "tidak hadir"});

        kelasA.proses();
        kelasB.proses();
    }
}