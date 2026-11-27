import json
from pathlib import Path

OUTPUT_DIR = Path(__file__).resolve().parent.parent / "output"


def save_report(city, data):
    """Saves an API response as output/<city>.json (pretty-printed), replacing any earlier file for that city."""
    # TODO: write `data` as JSON to output/<city>.json (create the folder if it's missing)
