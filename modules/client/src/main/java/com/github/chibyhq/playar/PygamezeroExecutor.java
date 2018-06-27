package com.github.chibyhq.playar;

import lombok.extern.java.Log;

@Log
public class PygamezeroExecutor extends PythonExecutor {

	public PygamezeroExecutor() {
        setPythonExecutable("pgzrun");
    }
}
