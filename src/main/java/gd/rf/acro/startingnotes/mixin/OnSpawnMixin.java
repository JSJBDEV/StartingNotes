package gd.rf.acro.startingnotes.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.commons.io.FileUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Mixin(ServerPlayerEntity.class)
public class OnSpawnMixin {
    @Inject(at = @At("TAIL"), method = "onSpawn")
    public void onSpawn(CallbackInfo ci)
    {
        ServerPlayerEntity player =((ServerPlayerEntity)(Object)this);
        if(!player.getScoreboardTags().contains("sn_spawned"))
        {
            try
            {
                List<String> books = FileUtils.readLines(new File("./config/StartingNotes/books.txt"),"utf-8");
                for (String book : books) {
                    List<String> abook = FileUtils.readLines(new File("./config/StartingNotes/"+book),"utf-8");
                    String author = abook.remove(0);
                    String title = abook.remove(0);
                    player.giveItemStack(createBook(author,title,abook.toArray()));
                }
                player.addScoreboardTag("sn_spawned");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static ItemStack createBook(String author, String title, Object ...pages)
    {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        CompoundTag tags = new CompoundTag();
        tags.putString("author",author);
        tags.putString("title",title);
        ListTag contents = new ListTag();
        for (Object page : pages) {
            contents.add(StringTag.of("{\"text\":\""+page+"\"}"));
        }
        tags.put("pages",contents);
        book.setTag(tags);
        return book;
    }
}
