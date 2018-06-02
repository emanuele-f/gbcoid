package com.androidemu.gbc.input;

public interface Keycodes
{
	public static final int GAMEPAD_UP = 0x0004;
	public static final int GAMEPAD_DOWN = 0x0008;
	public static final int GAMEPAD_LEFT = 0x0002;
	public static final int GAMEPAD_RIGHT = 0x0001;
	public static final int GAMEPAD_A = 0x0010;
	public static final int GAMEPAD_B = 0x0020;
	public static final int GAMEPAD_SELECT = 0x0040;
	public static final int GAMEPAD_START = 0x0080;
	public static final int GAMEPAD_A_TURBO = (GAMEPAD_A << 16);
	public static final int GAMEPAD_B_TURBO = (GAMEPAD_B << 16);

	public static final int GAMEPAD_UP_LEFT = (GAMEPAD_UP | GAMEPAD_LEFT);
	public static final int GAMEPAD_UP_RIGHT = (GAMEPAD_UP | GAMEPAD_RIGHT);
	public static final int GAMEPAD_DOWN_LEFT = (GAMEPAD_DOWN | GAMEPAD_LEFT);
	public static final int GAMEPAD_DOWN_RIGHT = (GAMEPAD_DOWN | GAMEPAD_RIGHT);
	public static final int GAMEPAD_AB = (GAMEPAD_A | GAMEPAD_B);
}
