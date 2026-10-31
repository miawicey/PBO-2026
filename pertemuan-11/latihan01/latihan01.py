class Kelas:
    def __init__(self, nama, daftar_teks):
        self.nama = nama
        self.daftar_teks = daftar_teks

    def rata_rata_nilai(self):
        nilai_valid = []
        for teks in self.daftar_teks:
            try:
                nilai_valid.append(int(teks))
            except ValueError:
                print(f"  '{teks}' bukan angka, dilewati")
        return sum(nilai_valid) // len(nilai_valid)

    def proses(self):
        print(f"=== {self.nama} ===")
        try:
            print(f"Rata-rata: {self.rata_rata_nilai()}")
        except ZeroDivisionError:
            print("Tidak ada nilai yang valid, rata-rata tidak bisa dihitung")
        print()


if __name__ == "__main__":
    kelas_a = Kelas("KELAS A", ["80", "75", "sembilan puluh", "92", ""])
    kelas_b = Kelas("KELAS B", ["-", "tidak hadir"])

    kelas_a.proses()
    kelas_b.proses()