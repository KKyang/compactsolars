/*******************************************************************************
 * Copyright (c) 2012 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors:
 *     cpw - initial API and implementation
 ******************************************************************************/
package cpw.mods.compactsolars.client;

import org.lwjgl.opengl.GL11;

import cpw.mods.compactsolars.CompactSolarType;
import cpw.mods.compactsolars.ContainerCompactSolar;
import cpw.mods.compactsolars.TileEntityCompactSolar;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GUISolar extends GuiContainer
{
    public static final ResourceLocation gui = new ResourceLocation("ic2", "textures/gui/GUISolarGenerator.png");

    public enum GUI
    {
        LOW_VOLTAGE(CompactSolarType.LOW_VOLTAGE), MEDIUM_VOLTAGE(CompactSolarType.MEDIUM_VOLTAGE), HIGH_VOLTAGE(CompactSolarType.HIGH_VOLTAGE);

        private CompactSolarType mainType;

        private GUI(CompactSolarType mainType)
        {
            this.mainType = mainType;
        }

        protected Container makeContainer(IInventory playerInventory, TileEntityCompactSolar solarTile)
        {
            return new ContainerCompactSolar(playerInventory, solarTile, this.mainType);
        }

        public static GUISolar buildGUI(CompactSolarType type, IInventory playerInventory, TileEntityCompactSolar solarTile)
        {
            for (GUI gui : values())
            {
                if (solarTile.getType() == gui.mainType)
                {
                    return new GUISolar(gui, playerInventory, solarTile);
                }
            }
            return null;
        }
    }

    private GUI type;
    private ContainerCompactSolar container;

    private GUISolar(GUI type, IInventory playerInventory, TileEntityCompactSolar solarTile)
    {
        super(type.makeContainer(playerInventory, solarTile));
        this.container = (ContainerCompactSolar) this.inventorySlots;
        this.type = type;
        this.allowUserInput = false;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRendererObj.drawString(this.type.mainType.friendlyName, 8, 6, 0x404040);
        this.fontRendererObj.drawString("Inventory", 8, (this.ySize - 96) + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(gui);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        if (this.container.tile.theSunIsVisible)
        {
            this.drawTexturedModalRect(x + 80, y + 45, 176, 0, 14, 14);
        }
    }

}
