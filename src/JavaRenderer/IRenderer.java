package JavaRenderer;

public interface IRenderer
{

	IDrawer getDrawer();

	void setDrawer(IDrawer drawer);

	void render(long delta);

	void update();
}
