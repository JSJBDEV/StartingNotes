package gd.rf.acro.startingnotes.mixin;


import gd.rf.acro.startingnotes.StartingNote;
import gd.rf.acro.startingnotes.StartingNotes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;


@Mixin(ServerPlayerEntity.class)
public class OnSpawnMixin {
    private static ItemStack createBook(String author, String title, List<String> pages) {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        NbtCompound tags = new NbtCompound();
        tags.putString("author", author);
        tags.putString("title", title);
        NbtList contents = new NbtList();
        for (Object page : pages) {
            contents.add(NbtString.of("{\"text\":\"" + page + "\"}"));
        }
        tags.put("pages", contents);
        book.setNbt(tags);
        return book;
    }
    
    @Inject(at = @At("TAIL"), method = "onSpawn")
    public void onSpawn(CallbackInfo ci) {
        ServerPlayerEntity player = ((ServerPlayerEntity) (Object) this);
        if (!player.getScoreboardTags().contains("sn_spawned")) {
            for (StartingNote note : StartingNotes.STARTING_NOTES) {
                player.giveItemStack(createBook(note.getAuthor(), note.getTitle(), note.getPages()));
            }
            player.addScoreboardTag("sn_spawned");
        }
    }
}
