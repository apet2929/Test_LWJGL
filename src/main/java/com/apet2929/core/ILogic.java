package com.apet2929.core;

import com.apet2929.core.mouse.MouseInput;

public interface ILogic {

    void init() throws Exception;

    void input();

    void update(MouseInput mouseInput);

    void render();

    void cleanup();
}
