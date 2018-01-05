package com.github.yin.spaceassets.game.generators;

import com.badlogic.ashley.core.Family.Builder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo;

public class SphereGenerator {
	private static final float base[] = { 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, };
	private short[] tris;
	private float[] verts;
	private int div;
	private int trisPos;
	private int vertPos;
	private int vertSize;
	private int triSize;

	public SphereGenerator(int div, int vertSize, int triSize) {
		this.div = div;
		this.vertSize = vertSize;
		this.triSize = triSize;
		tris = new short[triSize * getTriangleCount(div)];
		verts = new float[vertSize * getVerticleCount(div)];
		System.out.println("dev:" + div + " triC:" + tris.length + " vertC:" + verts.length);
	}

	private int getTriangleCount(int div) {
		return (div+1) * (div+1);
	}

	private int getVerticleCount(int div) {
		return (div + 1) * (div + 2) / 2;
	}

	public void generate(MeshPartBuilder meshBuilder, float w, float h, float d) {
		if (div < 1) {
			return;
		}

		for (int a = 0; a < div + 1; a++) {
			if (a == 0) {
				put(meshBuilder, base[3 * 0 + 0], base[3 * 0 + 1], base[3 * 0 + 2]);
			} else {
				float ad = ((float) a) / div;

				for (int b = 0; b < a + 1; b++) {
					float bc = ((float) b) / a;

					float d0 = base[3 * 1 + 0] * (1 - bc) + base[3 * 2 + 0] * bc;
					float d1 = base[3 * 1 + 1] * (1 - bc) + base[3 * 2 + 1] * bc;
					float d2 = base[3 * 1 + 2] * (1 - bc) + base[3 * 2 + 2] * bc;
					float out0 = base[3 * 0 + 0] * (1 - ad) + d0 * ad;
					float out1 = base[3 * 0 + 1] * (1 - ad) + d1 * ad;
					float out2 = base[3 * 0 + 2] * (1 - ad) + d2 * ad;
					float norm = (float) Math.pow(out0 * out0 + out1 * out1 + out2 * out2, -0.5);
					short vertIndex = 0;
					try {
						vertIndex = put(meshBuilder, out0 * norm, out1 * norm, out2 * norm);

					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("a:" + a + " b:" + b + " vP:" + vertPos + " tP:" + trisPos);
						throw e;
					}
					try {
 						if (b > 0) {
							triangle(meshBuilder, (short) (vertIndex - 1), (short) (vertIndex - (2 * a + 1)), vertIndex);

						}
						if (b < a) {
							//meshBuilder.triangle(vertIndex, (short) (vertIndex - (2 * a + 1)), (short) (vertIndex - 2 * a));
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("a:" + a + " b:" + b + " vP:" + vertPos + " tP:" + trisPos);
						throw e;
					}
				}
			}
		}
	}

	private void triangle(MeshPartBuilder meshBuilder, short s, short t, short vertIndex) {
		meshBuilder.triangle(s, t, vertIndex);
		System.out.println("vi:"+s+" "+t+" "+vertIndex);

	}

	public float[] getVerticleData() {
		float[] dest = new float[3*getVerticleCount(div)];
		System.arraycopy(verts, 0, dest, 0, 3 * vertPos);
		return dest;
	}

	public short[] getTriangleData() {
		short[] dest = new short[3*getTriangleCount(div)];
		System.arraycopy(tris, 0, dest, 0, 3 * trisPos);
		return dest;
	}

	private short put(MeshPartBuilder builder, float f, float g, float h) {
		System.out.println(f + "  " + g + "  " + h);

		return builder.vertex(new VertexInfo().setPos(f, g, h).setCol(f, g, h, 1.0f));
	}

	private short put(float f, float g, float h) {
		verts[vertSize * vertPos + 0] = f;
		verts[vertSize * vertPos + 1] = g;
		verts[vertSize * vertPos + 2] = h;
//		verts[vertSize * vertPos + 3] = f;
//		verts[vertSize * vertPos + 4] = g;
//		verts[vertSize * vertPos + 5] = h;
//		verts[vertSize * vertPos + 6] = 1.0f;
		return (short) vertPos++;
	}

	private int tri(int i, int j, int k) {
		tris[triSize * trisPos + 0] = (short) i;
		tris[triSize * trisPos + 1] = (short) j;
		tris[triSize * trisPos + 2] = (short) k;
		return trisPos++;
	}
}
