public class JamDigital {
    private int jam = 0;
    private int menit = 0;

    public void atur(int jam, int menit) {
        if (jam < 0 || jam > 23) {
            throw new IllegalArgumentException("jam harus 0 sampai 23");
        }
        if (menit < 0 || menit > 59) {
            throw new IllegalArgumentException("menit harus 0 sampai 59");
        }
        this.jam = jam;
        this.menit = menit;
    }

    public String tampilan() {
        return String.format("%02d:%02d", jam, menit);
    }

    public static void main(String[] args) {
        JamDigital jam = new JamDigital();
        System.out.println("=== JAM DIGITAL ===");
        System.out.println("Tampilan awal: " + jam.tampilan());

        int[][] percobaan = {{7, 30}, {25, 0}, {12, 75}, {23, 59}, {-1, 10}};
        for (int[] p : percobaan) {
            try {
                jam.atur(p[0], p[1]);
                System.out.println("Atur ke jam " + p[0] + " menit " + p[1] + " -> berhasil");
            } catch (IllegalArgumentException e) {
                System.out.println("Atur ke jam " + p[0] + " menit " + p[1] + " -> ditolak: " + e.getMessage());
            } finally {
                System.out.println("  Tampilan jam: " + jam.tampilan());
            }
        }
    }
}