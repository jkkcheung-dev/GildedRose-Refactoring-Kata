#!/usr/bin/env python
"""
This script uses npx to execute the TextTest Fixture for TypeScript.
It is designed to be used by TextTest and specified in the file 'texttests/config.gr' in this repo.
It is more convenient for TextTest to use since npx needs
several arguments in addition to the one the TextTest fixture needs.
"""
from pathlib import Path
import subprocess
import sys

project_root = Path(__file__).resolve().parent
fixture_path = project_root / 'test' / 'golden-master-text-test.ts'
ts_node_bin = project_root / 'node_modules' / 'ts-node' / 'dist' / 'bin.js'

subprocess.run(
    ['node', str(ts_node_bin), str(fixture_path), *sys.argv[1:]],
    check=False,
    cwd=project_root,
)
