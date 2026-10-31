class BarisDataError(Exception):
    def __init__(self, nomor_baris, alasan):
        self.nomor_baris = nomor_baris
        self.alasan = alasan
        super().__init__(f"Baris {nomor_baris}: {alasan}")


class Pendaftar:
    def __init__(self, nama, umur):
        self.nama = nama
        self.umur = umur

    def __str__(self):
        return f"{self.nama} ({self.umur} tahun)"


def baca_pendaftar(nomor_baris, teks):
    kolom = teks.split(";")
    if len(kolom) != 2:
        raise BarisDataError(nomor_baris, "format harus nama;umur")

    nama = kolom[0].strip()
    if not nama:
        raise BarisDataError(nomor_baris, "nama tidak boleh kosong")

    teks_umur = kolom[1].strip()
    try:
        umur = int(teks_umur)
    except ValueError as e:
        raise BarisDataError(nomor_baris, f"umur '{teks_umur}' bukan angka") from e

    if umur < 17 or umur > 25:
        raise BarisDataError(nomor_baris, f"umur {umur} di luar syarat 17-25 tahun")

    return Pendaftar(nama, umur)


if __name__ == "__main__":
    data = [
        "Ayu Lestari;20", "Bima Saputra", "Citra Dewi;dua puluh", ";19",
        "Dimas Pratama;30", "Eka Putri;18", "Fajar Nugraha;21;Samarinda",
    ]
    diterima = []
    ditolak = 0

    print("=== IMPOR DATA PENDAFTAR LOMBA DEBAT ===")
    for nomor, teks in enumerate(data, start=1):
        try:
            p = baca_pendaftar(nomor, teks)
            diterima.append(p)
            print(f"[OK] Baris {nomor}: {p}")
        except BarisDataError as e:
            ditolak += 1
            print(f"[DITOLAK] {e}")
            if e.__cause__ is not None:
                print("          (penyebab asli: kesalahan konversi angka)")

    print(f"\nDiterima: {len(diterima)}, ditolak: {ditolak}")
    print("Daftar peserta:")
    for p in diterima:
        print(f"- {p}")