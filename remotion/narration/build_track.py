import json
import subprocess
from pathlib import Path

HERE = Path(__file__).parent
SCRIPT = json.loads((HERE / "script.json").read_text(encoding="utf-8"))
AUDIO_DIR = HERE / "audio"
GAP_SEC = 0.5
OUT = HERE / "narration_full.wav"

inputs = []
filters = []
idx = 0
concat_labels = []

for item in SCRIPT:
    mp3 = AUDIO_DIR / f"{item['id']}.mp3"
    inputs += ["-i", str(mp3)]
    concat_labels.append(f"[{idx}:a]")
    idx += 1
    # silence gap after each clip
    inputs += ["-f", "lavfi", "-t", str(GAP_SEC), "-i", "anullsrc=r=24000:cl=mono"]
    concat_labels.append(f"[{idx}:a]")
    idx += 1

n = len(concat_labels)
filter_complex = "".join(concat_labels) + f"concat=n={n}:v=0:a=1[outa]"

cmd = ["ffmpeg", "-y", *inputs, "-filter_complex", filter_complex, "-map", "[outa]", str(OUT)]
print(" ".join(cmd))
subprocess.run(cmd, check=True)
