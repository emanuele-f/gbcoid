
#ifndef __FASTMEM_H__
#define __FASTMEM_H__


#include "defs.h"
#include "mem.h"
#include "cheats.h"


static inline byte readb_internal(int a)
{
	byte *p = mbc.rmap[a>>12];
	if (p) return p[a];
	else return mem_read(a);
}

static inline byte readb(int a)
{
	if (gbCheatMap[a])
		return gbCheatRead(a);

	return readb_internal(a);
}

extern int save_sram;

static inline void writeb(int a, byte b)
{
	byte *p = mbc.wmap[a>>12];

	if(((a>>12) & 0xE) == 0xA) save_sram = 1;

	if (p) p[a] = b;
	else mem_write(a, b);
}

static inline int readw(int a)
{
	if ((a+1) & 0xfff)
	{
		byte *p = mbc.rmap[a>>12];
		if (p)
		{
#ifdef IS_LITTLE_ENDIAN
#ifndef ALLOW_UNALIGNED_IO
			if (a&1) return p[a] | (p[a+1]<<8);
#endif
			return *(word *)(p+a);
#else
			return p[a] | (p[a+1]<<8);
#endif
		}
	}
	return mem_read(a) | (mem_read(a+1)<<8);
}

static inline void writew(int a, int w)
{
	if(((a>>12) & 0xE) == 0xA)
	{
	    save_sram = 1;
	}

	if ((a+1) & 0xfff)
	{
		byte *p = mbc.wmap[a>>12];
		if (p)
		{
#ifdef IS_LITTLE_ENDIAN
#ifndef ALLOW_UNALIGNED_IO
			if (a&1)
			{
				p[a] = w;
				p[a+1] = w >> 8;
				return;
			}
#endif
			*(word *)(p+a) = w;
			return;
#else
			p[a] = w;
			p[a+1] = w >> 8;
			return;
#endif
		}
	}
	mem_write(a, w);
	mem_write(a+1, w>>8);
}

static inline byte readhi(int a)
{
	return readb(a | 0xff00);
}

static inline void writehi(int a, byte b)
{
	writeb(a | 0xff00, b);
}

#if 0
static byte readhi(int a)
{
	byte (*rd)() = hi_read[a];
	return rd ? rd(a) : (ram.hi[a] | himask[a]);
}

static void writehi(int a, byte b)
{
	byte (*wr)() = hi_write[a];
	if (wr) wr(a, b);
	else ram.hi[a] = b & ~himask[a];
}
#endif


#endif
